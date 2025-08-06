import React, { useEffect, useState } from "react";
import axios from "axios";
import Apis, { endpoint } from "../configs/Apis";
import { useLocation, useParams } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Spinner, Alert, } from "react-bootstrap";
import CertificatePanel from "./CertificatePanel";
import MySpinner from "./layout/MySpinner";

function CourseDetail() {
    const [courseData, setCourseData] = useState(null);
    const [loading, setLoading] = useState(false);
    const { id } = useParams();
    const location = useLocation();

    const isEnrolled = location.state?.isEnrolled || false;

    const loadCourseDetail = async () => {

        try {
            setLoading(true);
            let url = `${endpoint.courseDetail(id)}`
            console.log(url)
            let res = await Apis.get(url);
            // console.log(res.data)
            setCourseData(res.data);
        } catch (e) {
            console.error("Lỗi load course detail:", e);
        } finally {
            setLoading(false);
        }
    }
    useEffect(() => {
        if (id) {
            loadCourseDetail();
        }

    }, [id]);

    if (loading || !courseData) return <MySpinner />;

    const { course, tags, lessonCount, enrollmentCount, feedback } = courseData;

    return (
        <Container className="mt-4">
            <Card>
                <Row>
                    <Col md={4}>
                        <Card.Img className="h-100" variant="top" src={course.imageUrl} />
                    </Col>
                    <Col md={8}>
                        <Card.Body>
                            <Card.Title className="fs-2">{course.title}</Card.Title>
                            <Card.Text>{course.description}</Card.Text>
                            <ListGroup variant="flush">
                                <ListGroup.Item><strong>Thời lượng:</strong> {course.duration}</ListGroup.Item>
                                <ListGroup.Item><strong>Trình độ:</strong> {course.level}</ListGroup.Item>
                                <ListGroup.Item><strong>Giá:</strong> {course.price} đ</ListGroup.Item>
                                <ListGroup.Item><strong>Lượt học:</strong> {enrollmentCount}</ListGroup.Item>
                                <ListGroup.Item><strong>Số bài học:</strong> {lessonCount}</ListGroup.Item>
                                <ListGroup.Item><strong>Tags:</strong> {tags.join(", ")}</ListGroup.Item>
                            </ListGroup>

                            {isEnrolled && (
                                <div className="mt-3">
                                    <CertificatePanel courseId={course.courseId} isEnrolled={true} />
                                </div>
                            )}
                        </Card.Body>
                    </Col>
                </Row>
            </Card>

            <Card className="mt-4">
                <Card.Header><strong>Phản hồi người học</strong></Card.Header>
                <Card.Body>
                    <p><strong>❤️ Lượt thích:</strong> {feedback.likes}</p>
                    <p><strong>⭐ Đánh giá:</strong> {feedback.ratings.average} ({feedback.ratings.count} lượt)</p>
                    <hr />
                    <h6>Bình luận:</h6>
                    {feedback.comments.map(cmt => (
                        <div key={cmt.commentId} className="mb-3 border-bottom pb-2">
                            <strong>{cmt.userFullName}</strong>
                            <p className="mb-1">{cmt.content}</p>
                            <small className="text-muted">
                                {new Date(cmt.createdAt).toLocaleString()}
                            </small>
                        </div>
                    ))}
                </Card.Body>
            </Card>
        </Container>
    );
}

export default CourseDetail;