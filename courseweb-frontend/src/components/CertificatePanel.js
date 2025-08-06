import React, { useContext, useState } from 'react';
import { Button, Alert, Spinner } from 'react-bootstrap';
import { authApis, endpoint } from '../configs/Apis';
import { MyUserContext } from '../configs/context';
import MySpinner from './layout/MySpinner';
import { Link } from 'react-router-dom';


const CertificatePanel = ({ courseId }) => {
    const [cert, setCert] = useState(null);
    const [message, setMessage] = useState(null);
    const [loading, setLoading] = useState(false);
    const [user,] = useContext(MyUserContext);

    if (!user || user.role !== 'USER') return null;

    const handleGenerateCertificate = async () => {
        setLoading(true);
        setMessage(null);

        try {
            const res = await authApis().post(endpoint.generateCertificate(courseId));
            setCert(res.data);
            setMessage('🎉 Cấp chứng chỉ thành công!');
        } catch (err) {
            console.error(err);
            setMessage('Không đủ điều kiện để cấp chứng chỉ hoặc đã có chứng chỉ.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="mt-4 border p-4 rounded bg-light">
            <h4 className="mb-3">📜 Chứng chỉ khóa học</h4>

            {message && <Alert variant="info">{message}</Alert>}

            <div className="d-flex gap-3 mb-4">
                <Button onClick={handleGenerateCertificate} disabled={loading}>
                    {loading ? <MySpinner /> : 'Cấp chứng chỉ'}
                </Button>
            </div>

            {cert && (
                <div className="p-3 border bg-white rounded">
                    <h5>🎓 Thông tin chứng chỉ</h5>
                    <p><strong>Học viên:</strong> {cert.userId?.fullName || cert.userId?.username}</p>
                    <p><strong>Khóa học:</strong> {cert.courseId?.title}</p>
                    <p><strong>Ngày cấp:</strong> {new Date(cert.issuedAt).toLocaleDateString()}</p>

                    {cert.certificateUrl ? (
                        <a href={cert.certificateUrl} download target="_blank" rel=" noreferrer">
                            👉 Tải chứng chỉ
                        </a>

                    ) : (
                        <p><em>Không có file chứng chỉ đính kèm.</em></p>
                    )}
                </div>
            )}
        </div>
    );
};

export default CertificatePanel;
