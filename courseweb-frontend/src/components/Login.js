import { useContext, useRef, useState } from "react";
import { Button, Col, Container, Row, Form, Card } from "react-bootstrap";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import Apis, { authApis, endpoint } from "../configs/Apis";
import cookie from 'react-cookies';
import MyCartReducer from "../reducers/MyCartReducer";
import { MyUserContext } from "../configs/context";
import MySpinner from "./layout/MySpinner";

const Login = () => {
    const [, dispatch] = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();
    const [q] = useSearchParams();

    const info = [
        {
            title: "Tên đăng nhập",
            field: "username",
            type: "text"
        },
        {
            title: "Mật khẩu",
            field: "password",
            type: "password"
        }
    ];

    const login = async (e) => {
        e.preventDefault();

        try {
            setLoading(true);
            let res = await Apis.post(endpoint.login, {
                ...user
            });

            cookie.save('token', res.data.token);

            let u = await authApis().get(endpoint.profile);

            console.log(u.data);
            dispatch({
                "type": "login",
                "payload": u.data
            });

            cookie.save('user', JSON.stringify(u.data)); 
            let next = q.get("next");
            console.log(next)
            nav(next?next:"/");
        } catch (e) {
            console.log(e.message);
        } finally {
            setLoading(false);
        }

    }


    const [user, setUser] = useState({});
    return (
        <>
            <Container className="py-5">
                <Row className="justify-content-center">
                    <Col xs={12} sm={10} md={8} lg={6} xl={5}>
                        <Card className="shadow-lg border-0">
                            <Card.Header className="bg-info text-center py-4">
                                <h3 className="mb-0 fw-bold">Đăng nhập tài khoản</h3>
                            </Card.Header>
                            <Card.Body className="p-4">
                                <Form onSubmit={login}>
                                    {info.map(inf => (
                                        <Row key={inf.field} className="mb-3">
                                            <Col>
                                                <Form.Group controlId={inf.field}>
                                                    <Form.Label className="fw-semibold text-dark"> {inf.title} </Form.Label>
                                                    <Form.Control
                                                        type={inf.type}
                                                        name={inf.field}
                                                        value={user[inf.field] || ""}
                                                        required
                                                        className="form-control-lg"
                                                        placeholder={`${inf.title}`}
                                                        onChange={e => setUser({ ...user, [inf.field]: e.target.value })}
                                                    />
                                                </Form.Group>
                                            </Col>
                                        </Row>
                                    ))}
                                    {loading ? <MySpinner /> : (<>
                                        <div className="d-grid gap-2 mt-4">
                                            <Button type="submit" variant="info" size="lg" className="fw-bold py-3">
                                                <i className="fas fa-user-plus me-2"></i>
                                                Đăng nhập
                                            </Button>
                                        </div>

                                    </>)}
                                </Form>
                            </Card.Body>
                            <Card.Footer className="text-center py-3 bg-light">
                                <small className="text-muted">
                                    Bạn chưa có tài khoản?
                                    <Link to="/register" className="text-primary text-decoration-none ms-1">
                                        Đăng ký ngay
                                    </Link>
                                </small>
                            </Card.Footer>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </>
    );
}



export default Login;