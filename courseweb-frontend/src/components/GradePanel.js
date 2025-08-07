import React, { useEffect, useState } from 'react';
import { authApis, endpoint } from '../configs/Apis';

const GradePanel = ({ lesson, student }) => {
    const [assessments, setAssessments] = useState([]);
    const [selectedAssessmentId, setSelectedAssessmentId] = useState(null);
    const [result, setResult] = useState(null);
    const [score, setScore] = useState('');
    const [feedback, setFeedback] = useState('');

    // Lấy danh sách bài đánh giá của lesson
    const loadAssessments = async () => {
        if (lesson) {
            try {
                const res = await authApis().get(endpoint.lessonAssessment(lesson.lessonId));
                setAssessments(res.data);
                setSelectedAssessmentId(res.data[0]?.assessmentId || null);
            } catch (err) {
                console.error("Không thể load assessments", err);
                setAssessments([]);
                setSelectedAssessmentId(null);
            }
        } else {
            setAssessments([]);
            setSelectedAssessmentId(null);
        }
    };
    useEffect(() => {
        loadAssessments();
    }, [lesson]);

    // Lấy kết quả đánh giá
    const loadResult = async () => {
        if (selectedAssessmentId && student) {
            try {
                const res = await authApis().get(endpoint.userAssessmentResult(selectedAssessmentId, student.userId));
                setResult(res.data);
                setScore(res.data?.score || '');
                setFeedback(res.data?.feedback || '');
            } catch (err) {
                setResult(null);  // chưa chấm
                setScore('');
                setFeedback('');
            }
        } else {
            setResult(null);
            setScore('');
            setFeedback('');
        }
    };
    useEffect(() => {
        loadResult();
    }, [selectedAssessmentId, student, lesson]);

    const handleGrade = async () => {
        if (!selectedAssessmentId || !student) {
            alert("Thiếu thông tin để chấm điểm.");
            return;
        }

        try {
            await authApis().post(endpoint.gradeAssessment(selectedAssessmentId, student.userId), null, {
                params: { score, feedback }
            });
            alert("Đã chấm điểm!");
        } catch (err) {
            console.error("Lỗi khi chấm điểm:", err);
            alert("Chấm điểm thất bại!");
        }
    };

    useEffect(() => {
        // Khi lesson hoặc student thay đổi => reset trước
        setScore('');
        setFeedback('');
        setResult(null);
    }, [lesson, student]);

    if (!lesson || !student)
        return <p>Vui lòng chọn bài học và học sinh.</p>;

    return (
        <div className="card p-3">
            <h5>{lesson.title}</h5>
            <p><strong>{student.fullName}</strong></p>

            <label>Chọn bài đánh giá:</label>
            <select className="form-select mb-2" value={selectedAssessmentId || ''} onChange={e => setSelectedAssessmentId(e.target.value)}>
                {assessments.map(a => (
                    <option key={a.assessmentId} value={a.assessmentId}>
                        {a.title}
                    </option>
                ))}
            </select>

            <label>Điểm:</label>
            <input
                type="number"
                className="form-control"
                value={score}
                onChange={e => setScore(e.target.value)}
            />

            <label className="mt-2">Ghi chú:</label>
            <textarea
                className="form-control"
                value={feedback}
                onChange={e => setFeedback(e.target.value)}
            />

            <button className="btn btn-dark mt-3" onClick={handleGrade}>
                Chấm bài
            </button>
        </div>
    );
};

export default GradePanel;
