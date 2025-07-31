import { useEffect, useState } from "react";
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import Apis, { endpoint } from "../../configs/Apis";

const Header = () => {
    const [tags, setTags] = useState([]);

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
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky="top" className="shadow-sm">
                <Container>
                    <Navbar.Brand href="/" className="fw-bold text-uppercase">
                        Course<span className="text-info">Web</span>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Link to="/" className="px-3 nav-link">Trang chủ</Link>
                            <NavDropdown title="Chủ đề liên quan" id="collapsible-nav-dropdown" menuVariant="dark" >
                                <Link  to={`/`} class="dropdown-item">Tất cả</Link>
                                {tags.map(t => (
                                    <Link key={t.tagId} to={`/?tagId=${t.tagId}`} class="dropdown-item">{t.name}</Link>
                                ))}
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