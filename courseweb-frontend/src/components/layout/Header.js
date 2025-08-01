import { useContext, useEffect, useState } from "react";
import { Alert, Badge, ButtonGroup, Container, Dropdown, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import Apis, { endpoint } from "../../configs/Apis";
import { MyCartContext, MyUserContext } from "../../configs/context";
import cookie from 'react-cookies';

const Header = () => {
    const [tags, setTags] = useState([]);
    const [cartCounter,] = useContext(MyCartContext);
    const [user, dispatch] = useContext(MyUserContext);

    const loadTags = async () => {
        let url = endpoint.tags;

        let res = await Apis.get(url);

        setTags(res.data);
    }
    useEffect(() => {
        loadTags();
    }, [])

    return (
        <>
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky="top" className="shadow-sm py-3">
                <Container>
                    <Navbar.Brand href="/" className="fw-bold text-uppercase">
                        Course<span className="text-info">Web</span>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Link to="/" className="px-3 nav-link">Trang chủ</Link>
                            <NavDropdown title="Chủ đề liên quan" id="collapsible-nav-dropdown" menuVariant="dark" >
                                <Link to={`/`} class="dropdown-item">Tất cả</Link>
                                {tags.map(t => (
                                    <Link key={t.tagId} to={`/?tagId=${t.tagId}`} class="dropdown-item">{t.name}</Link>
                                ))}
                            </NavDropdown>
                        </Nav>
                        <Nav>
                            <Nav.Link href="/cart" className="px-2 position-relative d-flex align-items-center gap-2">
                                <i className="bi bi-cart-fill fs-5"></i>
                                <span>Giỏ hàng</span>
                                <Badge bg="danger" pill className="position-absolute top-0 start-100 translate-middle">{cartCounter}</Badge>
                            </Nav.Link>
                            {user === null ? <>

                                <Nav.Link href="/login" className="px-2">Đăng nhập</Nav.Link>
                                <Nav.Link href="/register" className="px-2 btn btn-outline-info rounded-pill">Đăng ký</Nav.Link></> :
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