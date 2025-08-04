import { useContext, useEffect, useState } from "react";
import { Alert, Badge, Button, Card, Col, Container, Row, Spinner } from "react-bootstrap";
import { MyUserContext } from "../configs/context";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { authApis, endpoint } from "../configs/Apis";

const Cart = () => {
    const [user] = useContext(MyUserContext);
    const [cartItems, setCartItems] = useState([]);
    const [total, setTotal] = useState(0);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const [params] = useSearchParams();
    const statusPay = params.get("statusPay");

    const loadCart = async () => {
        try {
            const res = await authApis().get(endpoint["cart-items"]);
            setCartItems(res.data.items);
            // console.log(res.data.items)

            const totalRes = await authApis().get(endpoint["cart-total"]);
            setTotal(totalRes.data);
        } catch (err) {
            console.error("Lỗi tải giỏ hàng:", err);
        } finally {
            setLoading(false);
        }
    }
    useEffect(() => {
        if (user) {
            loadCart();
        }
        else
            setLoading(false);
    }, [user]);

    const handleRemove = async (id) => {
        if (window.confirm("Bạn có chắc muốn xóa khóa học này?")) {
            try {
                await authApis().delete(`${endpoint.cartDelete(id)}`);
                setCartItems(prev => prev.filter(item => item.cartItemId !== id));
            } catch (err) {
                console.error("Lỗi xóa mục khỏi giỏ:", err);
            }
        }
    }

    const handleCheckout = async () => {
        try {
            const res = await authApis().post(endpoint["checkout"]);
            const vnpUrl = res.data.vnpUrl;

            if (vnpUrl) {
                try {
                    setTimeout(() => {
                        window.location.href = vnpUrl;
                    }, 500);
                    window.location.href = vnpUrl;
                } catch (e) {
                    alert("Không thể chuyển hướng tự động. Nhấn OK để mở thủ công.");
                    window.open(vnpUrl, "_blank");
                }
            }
        } catch (err) {
            alert("Không thể tạo giao dịch thanh toán.");
        }
    }

    const getTotal = async () => {
        const res = await authApis().get(endpoint["cart-total"]);
        setTotal(res.data.total);
    }

    useEffect(() => {
        getTotal();
    }, [cartItems]);

    useEffect(() => {
        if (statusPay) {
            alert(`Thanh toán ${statusPay}`);
            navigate("/cart", { replace: true });
        }
    }, [statusPay]);


    if (loading) return <div className="text-center mt-5"><Spinner animation="border" /></div>;

    if (!user)
        return (
            <Alert variant="warning" className="mt-4 text-center shadow-sm">
                <Alert.Heading>⚠ Bạn chưa đăng nhập!</Alert.Heading>
                <p>Vui lòng <Link to="/login?next=/cart" className="fw-bold text-decoration-underline">đăng nhập</Link> để xem và thanh toán giỏ hàng.</p>
            </Alert>
        );

    return (
        <>
            <Container className="py-4">
                <Row className="justify-content-center">
                    <Col lg={8}>
                        <div className="d-flex align-items-center justify-content-between mb-4">
                            <div className="d-flex align-items-center">
                                <h3 className="mb-0 me-3">
                                    <i className="bi bi-cart3 text-primary me-2"></i>
                                    Giỏ hàng của bạn
                                </h3>
                                {cartItems.length > 0 && (
                                    <Badge bg="primary" pill className="fs-6">
                                        {cartItems.length} khóa học
                                    </Badge>
                                )}
                            </div>
                        </div>

                        {cartItems.length === 0 ? (
                            <Card className="border-0 shadow-sm">
                                <Card.Body className="text-center py-5">
                                    <div className="mb-4">
                                        <i className="bi bi-cart-x display-1 text-muted"></i>
                                    </div>
                                    <h4 className="text-muted mb-3">Giỏ hàng trống</h4>
                                    <p className="text-muted mb-4">
                                        Bạn chưa thêm khóa học nào vào giỏ hàng
                                    </p>
                                    <Button variant="primary" size="lg">
                                        <i className="bi bi-search me-2"></i>
                                        Khám phá khóa học
                                    </Button>
                                </Card.Body>
                            </Card>
                        ) : (
                            <>
                                <div className="mb-4">
                                    {cartItems.map((cart, index) => (
                                        <Card key={cart.cartItemId} className="mb-3 border-0 shadow-sm">
                                            <Card.Body className="p-4">
                                                <Row className="align-items-center">
                                                    <Col xs={12} sm={3} className="mb-3 mb-sm-0">
                                                        <div className="position-relative">
                                                            <img
                                                                src={cart.imageUrl}
                                                                alt={cart.title}
                                                                className="img-fluid rounded"
                                                            />
                                                        </div>
                                                    </Col>

                                                    <Col xs={12} sm={6} className="mb-3 mb-sm-0">
                                                        <h5 className="mb-2 fw-bold">{cart.title}</h5>
                                                        <div className="d-flex align-items-center">
                                                            <h4 className="text-success mb-0 fw-bold">
                                                                {cart.price?.toLocaleString()} ₫
                                                            </h4>
                                                        </div>
                                                    </Col>

                                                    <Col xs={12} sm={3} className="text-sm-end">
                                                        <Button
                                                            variant="outline-danger"
                                                            size="sm"
                                                            className="w-100 w-sm-auto"
                                                            onClick={() => handleRemove(cart.cartItemId)}
                                                        >
                                                            <i className="bi bi-trash me-2"></i>
                                                            Xóa khỏi giỏ
                                                        </Button>
                                                    </Col>
                                                </Row>
                                            </Card.Body>
                                        </Card>
                                    ))}
                                </div>

                                <Card className="border-0 shadow-sm bg-light">
                                    <Card.Body className="p-4">
                                        <Row className="align-items-center">
                                            <Col xs={12} md={6} className="mb-3 mb-md-0">
                                                <div className="d-flex justify-content-between align-items-center">
                                                    <span className="fs-5 fw-bold">Tổng cộng:</span>
                                                    <h3 className="text-primary mb-0 fw-bold">
                                                        {total.toLocaleString()} ₫
                                                    </h3>
                                                </div>
                                            </Col>
                                            <Col xs={12} md={6} className="text-md-end">
                                                <Button
                                                    variant="success"
                                                    size="lg"
                                                    className="w-100 w-md-auto px-5"
                                                    onClick={handleCheckout}
                                                >
                                                    <i className="bi bi-credit-card me-2"></i>
                                                    Thanh toán ngay
                                                </Button>
                                            </Col>
                                        </Row>
                                    </Card.Body>
                                </Card>

                                <Alert variant="info" className="mt-4 border-0">
                                    <div className="d-flex align-items-center">
                                        <i className="bi bi-info-circle me-2"></i>
                                        <small>
                                            Bạn sẽ có quyền truy cập trọn đời vào các khóa học sau khi thanh toán thành công.
                                        </small>
                                    </div>
                                </Alert>
                            </>
                        )}
                    </Col>
                </Row>
            </Container>
        </>
    );
}
export default Cart;
