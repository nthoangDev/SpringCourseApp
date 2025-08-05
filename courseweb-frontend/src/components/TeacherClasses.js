import React, { useEffect, useState } from 'react';
import { authApis, endpoint } from '../configs/Apis';
import { useNavigate } from 'react-router-dom';

const TeacherClasses = () => {
  const [classes, setClasses] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const loadClasses = async () => {
      const res = await authApis().get(endpoint["teacher-classes"]);
      setClasses(res.data);
    };
    loadClasses();
  }, []);

  return (
    <div className="container mt-4">
      <h3>Bài tập về nhà</h3>
      <div className="list-group">
        {classes.map(c => (
          <button
            key={c.courseId}
            className="list-group-item list-group-item-action"
            onClick={() => navigate(`/grade/${c.courseId}`)}
          >
            <strong>{c.title}</strong> <br />
            3 học viên
          </button>
        ))}
      </div>
    </div>
  );
};

export default TeacherClasses;
