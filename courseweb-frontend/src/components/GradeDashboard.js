import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { authApis, endpoint } from '../configs/Apis';
import GradePanel from './GradePanel';

const GradeDashboard = () => {
    const { courseId } = useParams();
    const [lessons, setLessons] = useState([]);
    const [students, setStudents] = useState([]);
    const [selectedLesson, setSelectedLesson] = useState(null);
    const [selectedStudent, setSelectedStudent] = useState(null);

    useEffect(() => {
        const loadData = async () => {
            const [lessonRes, studentRes] = await Promise.all([
                authApis().get(endpoint.courseLessons(courseId)),
                authApis().get(endpoint.courseStudents(courseId))
            ]);
            setLessons(lessonRes.data);
            setStudents(studentRes.data);
        };
        loadData();
    }, [courseId]);

    return (
        <div className="container mt-3 d-flex gap-3">
            <div className="col-4">
                <h5>Bài học</h5>
                <div className="list-group">
                    {lessons.map(lesson => (
                        <button
                            key={lesson.lessonId}
                            className={`list-group-item ${selectedLesson?.lessonId === lesson.lessonId ? "active" : ""}`}
                            onClick={() => setSelectedLesson(lesson)}
                        >
                            {lesson.title}
                        </button>
                    ))}
                </div>
            </div>

            <div className="col-3">
                <h5>Học sinh</h5>
                <div className="list-group">
                    {students.map(student => (
                        <button
                            key={student.userId}
                            className={`list-group-item ${selectedStudent?.userId === student.userId ? "active" : ""}`}
                            onClick={() => setSelectedStudent(student)}
                        >
                            {student.fullName}
                        </button>
                    ))}
                </div>
            </div>

            <div className="col-5">
                {selectedLesson && selectedStudent ? (
                    <GradePanel lesson={selectedLesson} student={selectedStudent} />
                ) : (
                    <p>Vui lòng chọn bài học và học sinh để chấm điểm.</p>
                )}
            </div>
        </div>
    );
};

export default GradeDashboard;
