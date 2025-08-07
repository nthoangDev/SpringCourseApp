import { useContext, useEffect, useState } from "react";
import { Alert, Badge, ButtonGroup, Container, Dropdown, Nav, Navbar, NavDropdown, Spinner } from "react-bootstrap";
import { Link, useLocation } from "react-router-dom";
import Apis, { authApis, endpoint } from "../../configs/Apis";
import { MyCartContext, MyUserContext, MyNotificationContext } from "../../configs/context";
import cookie from 'react-cookies';

const Header = () => {
  const [tags, setTags] = useState([]);
  const [cartCounter,] = useContext(MyCartContext);
  const [user, dispatch] = useContext(MyUserContext);
  const [reminderCount, dispatchReminder] = useContext(MyNotificationContext);
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(false);
  const location = useLocation();

  const loadTags = async () => {
    let url = endpoint.tags;

    let res = await Apis.get(url);

    setTags(res.data);
  }
  const loadNotifications = async () => {
    setLoading(true);
    try {
      const countRes = await authApis().get(endpoint.reminderCount);
      dispatchReminder({ type: "setReminderCount", payload: countRes.data });
      const notifRes = await authApis().get(endpoint.notifications);
      setNotifications(notifRes.data);
    } catch (err) {
      console.error("Lỗi khi tải thông báo:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTags();
    if (user) loadNotifications();
  }, [user]);

  return (
    <>
      <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky="top" className="shadow-sm py-3">
        <Container>
          <Link to="/" className="fw-bold text-uppercase navbar-brand">
            Course<span className="text-info">Web</span>
          </Link>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <Link to="/" className="px-3 nav-link">Trang chủ</Link>
              <NavDropdown title="Chủ đề liên quan" id="collapsible-nav-dropdown" menuVariant="dark" className="px-3">
                <Link to={`/`} class="dropdown-item">Tất cả</Link>
                {tags.map(t => (
                  <Link key={t.tagId} to={`/?tagId=${t.tagId}`} class="dropdown-item">{t.name}</Link>
                ))}
              </NavDropdown>
              <Link to="/my-courses" className="px-3 nav-link">
                Khóa của tôi
              </Link>

              {user?.role === "TEACHER" && (
                <Link to="/grade" className="px-3 nav-link">Chấm điểm</Link>
              )}
            </Nav>

            {user && (
              <Dropdown
                as={ButtonGroup}
                align="end"
                className="ms-3"
                onToggle={(isOpen) => {
                  if (isOpen) loadNotifications();
                }}
              >
                <Dropdown.Toggle
                  aria-label="Thông báo"
                  variant="dark"
                  className="d-flex align-items-center gap-2 border-0 shadow-none text-light"
                >
                  <i className="bi bi-bell-fill fs-5" />
                  <Badge bg="danger" pill>
                    {reminderCount}
                  </Badge>
                </Dropdown.Toggle>

                <Dropdown.Menu
                  variant="dark"
                  className="w-100"
                  style={{
                    minWidth: "280px",
                    fontSize: "1rem",
                    padding: "0.75rem",
                    maxHeight: "240px",
                    overflowY: "auto",
                  }}
                >
                  <Dropdown.Item
                    as="button"
                    className="text-center text-info"
                    onClick={async (e) => {
                      e.preventDefault();
                      e.stopPropagation();
                      try {
                        const courseMatch = location.pathname.match(/^\/courses\/(\d+)/);
                        if (courseMatch) {
                          const cid = courseMatch[1];
                          await authApis().post(endpoint.studyReminder(cid));
                        }
                      } catch (err) {
                        console.error("Lỗi gửi study reminder:", err);
                      }
                      loadNotifications();
                    }}
                  >
                    🔄 Làm mới
                  </Dropdown.Item>

                  <Dropdown.Divider />

                  {loading ? (
                    <Dropdown.Item disabled>
                      <Spinner animation="border" size="sm" /> Đang tải...
                    </Dropdown.Item>
                  ) : notifications.length === 0 ? (
                    <Dropdown.Item disabled>Không có thông báo</Dropdown.Item>
                  ) : (
                    notifications.map((n, i) => (
                      <Dropdown.Item
                        key={i}
                        as={Link}
                        to="#"
                        style={{ whiteSpace: "normal" }}
                      >
                        <i className="bi bi-info-circle me-2" />
                        {n.message}
                      </Dropdown.Item>
                    ))
                  )}
                </Dropdown.Menu>
              </Dropdown>
            )}


            <Nav>
              <Link to="/cart" className="px-3 position-relative d-flex align-items-center gap-2 nav-link">
                <i className="bi bi-cart-fill fs-5"></i>
                <span>Giỏ hàng</span>
                <Badge bg="danger" pill className="position-absolute top-0 start-100 translate-middle">{cartCounter}</Badge>
              </Link>
              {user === null ? <>

                <Link to="/login" className="px-2 text-warning nav-link">Đăng nhập</Link>
                <Link to="/register" className="px-2 nav-link ">Đăng ký</Link></> :
                <>
                  <Dropdown as={ButtonGroup} align="end" className="ms-3">
                    <Dropdown.Toggle
                      variant="dark"
                      className="d-flex align-items-center gap-2 border-0 shadow-none text-light"
                    >
                      <img
                        src={user.avatarUrl || "https://via.placeholder.com/32"}
                        alt="avatar"
                        width="32"
                        height="32"
                        className="rounded-circle border border-light"
                      />
                      <span className="fw-semibold">{user.fullName}</span>
                    </Dropdown.Toggle>

                    <Dropdown.Menu variant="dark" className="w-100">
                      <Link to="secure/profile" className="dropdown-item">
                        <i className="bi bi-person-circle me-2"></i>Trang cá nhân
                      </Link>

                      <Dropdown.Divider />

                      <Link
                        to="#"
                        className="dropdown-item text-danger"
                        onClick={() => {
                          cookie.remove('user');
                          dispatch({ type: "logout" });
                        }}
                      >
                        <i className="bi bi-box-arrow-right me-2"></i>Đăng xuất
                      </Link>
                    </Dropdown.Menu>
                  </Dropdown>
                </>}

            </Nav>

          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
}

export default Header;