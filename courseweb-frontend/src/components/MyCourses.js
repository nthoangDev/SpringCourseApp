// src/components/MyCourses.js
import React, { useEffect, useState } from "react";
import Apis, { endpoint, authApis } from "../configs/Apis";
import { Container, Row, Col, Card, Button, Spinner, Alert } from "react-bootstrap";

export default function MyCourses() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const load = async () => {
            try {
                const res = await authApis().get(endpoint.myCourses);
                setCourses(res.data);
                setError("");
            } catch (err) {
                console.error(err);
                // Chỉ lấy status + message ngắn gọn
                if (err.response) {
                    if (err.response.status === 401) {
                        setError("Vui lòng đăng nhập để xem khóa học của bạn.");
                    } else {
                        setError(`Lỗi ${err.response.status}: ${err.response.statusText}`);
                    }
                } else {
                    setError("Không thể kết nối tới server.");
                }
            } finally {
                setLoading(false);
            }
        };
        load();
    }, []);

    if (loading) {
        return <Container className="text-center mt-5"><Spinner animation="border" /></Container>;
    }
    if (error) {
        return <Container className="mt-5"><Alert variant="warning">{error}</Alert></Container>;
    }
    return (
        <Container className="mt-4">
            <h2>Khóa học của tôi</h2>
            {courses.length === 0 && (
                <Alert variant="info">Bạn chưa đăng ký khóa học nào.</Alert>
            )}
            <Row>
                {courses.map(c => (
                    <Col key={c.courseId} md={4} className="mb-4">
                        <Card>
                            {c.imageUrl && (
                                <Card.Img
                                    variant="top"
                                    src={c.imageUrl}
                                    style={{ height: 180, objectFit: "cover" }}
                                />
                            )}
                            <Card.Body>
                                <Card.Title>{c.title}</Card.Title>
                                <Card.Text className="text-truncate">{c.description}</Card.Text>
                                <Button
                                    variant="primary"
                                    onClick={() =>
                                        window.location.href = `/CourseWeb/courses/${c.courseId}/content`
                                    }
                                >
                                    Xem nội dung
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
}
