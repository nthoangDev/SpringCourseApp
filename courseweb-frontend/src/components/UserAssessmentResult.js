import React, { useEffect, useState } from 'react';
import { Card, Alert, Spinner } from 'react-bootstrap';
import { authApis, endpoint } from '../configs/Apis';
import MySpinner from './layout/MySpinner';

const UserAssessmentResult = ({ assessmentId, userId }) => {
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchResult = async () => {
      setLoading(true);
      try {
        const res = await authApis().get(endpoint.userAssessmentResult(assessmentId, userId));
        setResult(res.data);
      } catch (err) {
        setError('Không tìm thấy kết quả hoặc lỗi khi tải dữ liệu.');
        setResult(null);
      } finally {
        setLoading(false);
      }
    };

    if (assessmentId && userId) fetchResult();
  }, [assessmentId, userId]);

  if (loading) return <MySpinner/>;
  if (error) return <Alert variant="danger">{error}</Alert>;
  if (!result) return <p>Chưa có kết quả đánh giá.</p>;

  return (
    <Card className="mt-3">
      <Card.Header>Kết quả đánh giá</Card.Header>
      <Card.Body>
        <p><strong>Học viên:</strong> {result.users?.fullName}</p>
        <p><strong>Điểm:</strong> {result.score}</p>
        <p><strong>Nhận xét:</strong> {result.feedback || "Không có nhận xét"}</p>
        <p><strong>Hoàn thành lúc:</strong> {new Date(result.completedAt).toLocaleString()}</p>
      </Card.Body>
    </Card>
  );
};

export default UserAssessmentResult;
