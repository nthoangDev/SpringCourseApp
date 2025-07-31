import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";

const Header = () => {
    return (
        <>
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky="top" className="shadow-sm">
                <Container>
                    <Navbar.Brand href="/" className="fw-bold text-uppercase">
                        Course<span className="text-info">Web</span>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="/" className="px-3">Trang chủ</Nav.Link>
                            <Nav.Link href="/courses" className="px-3">Khóa học</Nav.Link>
                            <NavDropdown title="Tài khoản" id="collapsible-nav-dropdown">
                                <NavDropdown.Item href="/profile">Hồ sơ</NavDropdown.Item>
                                <NavDropdown.Item href="/settings">Cài đặt</NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item href="/logout">Đăng xuất</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <Nav>
                            <Nav.Link href="/login" className="px-2">Đăng nhập</Nav.Link>
                            <Nav.Link href="/register" className="px-2 btn btn-outline-info rounded-pill">Đăng ký</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}

export default Header;