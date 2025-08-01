import { useRef, useState } from "react";
import { Button, Col, Container, Row, Form, Card, Alert } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import Apis, { endpoint } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";


const Register = () => {
    const info = [
        {
            title: "Tên đăng nhập",
            field: "username",
            type: "text"
        },
        {
            title: "Email",
            field: "email",
            type: "email"
        },
        {
            title: "Mật khẩu",
            field: "password",
            type: "password"
        },
        {
            title: "Xác nhận mật khẩu",
            field: "confirm",
            type: "password"
        },
        {
            title: "Họ và tên",
            field: "fullname",
            type: "text"
        },
        {
            title: "Ảnh đại diện",
            field: "avatar",
            type: "file"
        }
    ];

    const avatar = useRef();
    const [user, setUser] = useState({});
    const [msg, setMsg] = useState();
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const validate = () => {
        if (user.confirm !== user.password) {
            setMsg("Mật khẩu không khớp!");
            return false;
        }

        setMsg(null);
        return true;
    };
    const register = async (event) => {
        event.preventDefault();

        if (validate()) {
            try {
                setLoading(true);
                let formData = new FormData();
                for (let key in user) {
                    if (key !== "confirm") {
                        formData.append(key, user[key]);
                    }
                }
                if (avatar.current.files.length > 0) {
                    formData.append("avatarUrl", avatar.current.files[0]);
                }

                let res = await Apis.post(endpoint.register, formData, {
                    headers: {
                        'Content-Type': "multipart/form-data"
                    }
                });

                if (res.status == 201) {
                    nav('/login');
                } else {
                    setMsg("Đăng ký thật bại");
                }
            } catch (e) {
                console.log(e.message());
            }
            finally {
                setLoading(true);
            }
        }
    }

    return (
        <>
            <Container className="py-5">
                <Row className="justify-content-center">
                    <Col xs={12} sm={10} md={8} lg={6} xl={5}>
                        <Card className="shadow-lg border-0">
                            <Card.Header className="bg-info text-center py-4">
                                <h3 className="mb-0 fw-bold">Đăng ký tài khoản</h3>
                            </Card.Header>
                            <Card.Body className="p-4">
                                <Form onSubmit={register}>
                                    {info.map(inf => (
                                        <Row key={inf.field} className="mb-3">
                                            <Col>
                                                <Form.Group controlId={inf.field}>
                                                    <Form.Label className="fw-semibold text-dark"> {inf.title} </Form.Label>
                                                    {inf.type === "file" ? (
                                                        <Form.Control type="file" name={inf.field} ref={avatar} className="form-control-lg" />
                                                    ) : (
                                                        <Form.Control
                                                            type={inf.type}
                                                            name={inf.field}
                                                            value={user[inf.field] || ""}
                                                            required
                                                            className="form-control-lg"
                                                            placeholder={`${inf.title}`}
                                                            onChange={e => setUser({ ...user, [inf.field]: e.target.value })}
                                                        />
                                                    )}
                                                </Form.Group>
                                            </Col>
                                        </Row>
                                    ))}
                                    {msg && (
                                        <Alert variant="danger" className="text-center fw-semibold">
                                            {msg}
                                        </Alert>
                                    )}
                                    {loading ? <MySpinner /> : (<>
                                        <div className="d-grid gap-2 mt-4">
                                            <Button type="submit" variant="info" size="lg" className="fw-bold py-3">
                                                <i className="fas fa-user-plus me-2"></i>
                                                Đăng ký
                                            </Button>
                                        </div>

                                    </>)}

                                </Form>
                            </Card.Body>
                            <Card.Footer className="text-center py-3 bg-light">
                                <small className="text-muted">
                                    Đã có tài khoản?
                                    <Link to="/login" className="text-primary text-decoration-none ms-1">
                                        Đăng nhập ngay
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

export default Register;