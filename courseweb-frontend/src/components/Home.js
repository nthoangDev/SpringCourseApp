import { useEffect, useState } from "react";
import { Alert, Button, Card, Col, Container, Form, Row, Spinner } from "react-bootstrap";
import Apis, { endpoint } from "../configs/Apis";
import { Link, useSearchParams } from "react-router-dom";

const Home = () => {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(false);
    const [keyword, setKeyword] = useState([]);
    const [page, setPage] = useState(1);
    const [level, setLevel] = useState('');
    const [sort, setSort] = useState('');
    const [params] = useSearchParams();

    const loadCourses = async () => {
        let url = `${endpoint.courses}?page=${page}`;

        if (keyword) {
            url = `${url}&kw=${keyword}`;
        }
        if (level) {
            url += `&level=${level}`;
        }
        if (sort) {
            url += `&orderBy=${sort}`;
        }

        let tagId = params.get("tagId");
        if (tagId) {
            url = `${url}&tagId=${tagId}`;
        }


        try {
            setLoading(true);
            let res = await Apis.get(url);

            if (res.data.length === 0) {
                page = 0;
            } else {
                if (page <= 1) {
                    setCourses(res.data);
                } else {
                    setCourses([...courses, ...res.data]);
                }
            }


        } catch (e) {
            console.error("Lỗi load courses:", e);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        setLoading(true);
        let timer = setTimeout(() => {
            if (page > 0) {
                loadCourses();
            }
        }, 500);

        return () => clearTimeout(timer);
    }, [page, keyword, params, level, sort]);

    useEffect(() => {
        setPage(1);
    }, [keyword, params,  level, sort]);

    const loadMore = () => {
        setPage(page + 1);
    }


    return (
        <>

            <Container>
                {loading ? <Spinner animation="border" variant="info" /> :
                    <>
                        <Form className="my-4">
                            <Row className="align-items-center g-2">
                                <Col xs={12} md={4}>
                                    <Form.Control type="text" placeholder="Tìm kiếm khóa học..." value={keyword} onChange={(e) => setKeyword(e.target.value)} />
                                </Col>
                                <Col xs={6} md={3}>
                                    <Form.Select value={level} onChange={(e) => setLevel(e.target.value)} >
                                        <option value="">-- Chọn trình độ --</option> 
                                        <option value="BEGINNER">Cơ bản</option>
                                        <option value="INTERMEDIATE">Trung cấp</option>
                                        <option value="ADVANCED">Nâng cao</option>
                                    </Form.Select>
                                </Col>
                                <Col xs={6} md={3}>
                                    <Form.Select value={sort} onChange={(e) => setSort(e.target.value)} >
                                        <option value="">-- Sắp xếp --</option>
                                        <option value="price_asc">Giá tăng dần</option>
                                        <option value="price_desc">Giá giảm dần</option>
                                    </Form.Select>
                                </Col>
                                <Col xs={12} md={2}>
                                    <Button variant="primary" className="w-100" > Tìm kiếm </Button>
                                </Col>
                            </Row>
                        </Form>
                        <Row>
                            {Array.isArray(courses) && courses.length === 0 && (
                                <Alert variant="light" className="mt-5 text-center border shadow-sm">
                                    <div className="d-flex flex-column align-items-center">
                                        <i className="bi bi-emoji-frown fs-1 text-secondary mb-3"></i>
                                        <h5 className="mb-1">Không có khóa học nào phù hợp</h5>
                                        <p className="text-muted">Vui lòng thử lại với từ khóa khác hoặc bỏ bộ lọc</p>
                                    </div>
                                </Alert>
                            )}
                            {courses.map(c => (
                                <Col key={c.courseId} md={3} xs={6} className="p-2">
                                    <Card>
                                        <Card.Img variant="top" src={c.imageUrl} alt={c.title} className="card-img" />
                                        <Card.Body>
                                            <Card.Title className="card-title">{c.title}</Card.Title>

                                            <Card.Text className="card-text text-truncate-3">{c.description}</Card.Text>

                                            <Card.Text className="card-info">
                                                <strong>Giá:</strong> {c.price?.toLocaleString('vi-VN')} đ<br />
                                                <strong>Thời lượng:</strong> {c.duration || 'Không rõ'}<br />
                                                <strong>Cấp độ:</strong> {c.level}
                                            </Card.Text>

                                            <div className="card-buttons">
                                                <Link to={`/courses/${c.courseId}`} className="btn btn-primary btn-sm" >Xem chi tiết</Link>
                                                <Link className="btn btn-success btn-sm" >Đăng ký</Link>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            ))}
                        </Row>
                        <div className="my-3 text-center">
                            <Button variant="info" className="px-4 fw-bold" onClick={loadMore}>Xem thêm ...</Button>
                        </div>

                    </>}
            </Container>



        </>
    );
}

export default Home;