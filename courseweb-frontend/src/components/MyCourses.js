import { useEffect, useState } from "react";
import { endpoint, authApis } from "../configs/Apis";
import { Container, Row, Col, Card, Spinner, Alert, ProgressBar } from "react-bootstrap";
import { Link } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

export default function MyCourses() {
  const [courses, setCourses] = useState([]);
  const [progressMap, setProgressMap] = useState({}); // { [courseId]: { completedLessons, totalLessons, percent, completed } }
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        // 1) Lấy danh sách khóa đã đăng ký
        const res = await authApis().get(endpoint.myCourses);
        setCourses(res.data);

        // 2) Với mỗi course, gọi API progress
        const promises = res.data.map(async c => {
          try {
            const r = await authApis().get(
              `${endpoint.myCourses.replace("/secure/my-courses", `/secure/courses/${c.courseId}/progress`)}`
            );
            return [c.courseId, r.data];
          } catch {
            return [c.courseId, null];
          }
        });
        const entries = await Promise.all(promises);
        setProgressMap(Object.fromEntries(entries));

        setError("");
      } catch (err) {
        console.error(err);
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
    return (
      <Container className="text-center mt-5">
        <MySpinner />
      </Container>
    );
  }
  if (error) {
    return (
      <Container className="mt-5">
        <Alert variant="warning">{error}</Alert>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <h2>Khóa học của tôi</h2>
      {courses.length === 0 && <Alert variant="info">Bạn chưa đăng ký khóa học nào.</Alert>}
      <Row>
        {courses.map(c => {
          const prog = progressMap[c.courseId];
          return (
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

                  {prog ? (
                    <>
                      <div className="mb-2">
                        {prog.completedLessons} / {prog.totalLessons} bài
                      </div>
                      <ProgressBar
                        now={prog.percent}
                        label={`${Math.round(prog.percent)}%`}
                        animated
                      />
                      {prog.completed && (
                        <small className="text-success">Đã hoàn thành khóa</small>
                      )}
                    </>
                  ) : (
                    <small className="text-muted">Không lấy được tiến độ</small>
                  )}

                  <Link
                    className="btn btn-primary mt-3"
                    to={`/courses/${c.courseId}`}
                    state={{ isEnrolled: true }}
                  >
                    Xem nội dung
                  </Link>
                </Card.Body>
              </Card>
            </Col>
          );
        })}
      </Row>
    </Container>
  );
}
