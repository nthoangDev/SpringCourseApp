// src/components/CourseProgress.js
import React, { useEffect, useState } from "react";
import { ProgressBar, Spinner, Alert } from "react-bootstrap";
import { authApis, endpoint } from "../configs/Apis";

export default function CourseProgress({ courseId }) {
  const [progress, setProgress] = useState(null);
  const [err, setErr] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        const res = await authApis().get(endpoint.courseProgress(courseId));
        setProgress(res.data);
      } catch (e) {
        console.error(e);
        setErr("Không lấy được tiến độ");
      }
    };
    load();
  }, [courseId]);

  if (err) return <Alert variant="warning">{err}</Alert>;
  if (!progress)
    return <Spinner animation="border" size="sm" className="my-1" />;

  return (
    <div className="mt-2">
      <small>
        Đã học {progress.completedLessons}/{progress.totalLessons} bài
      </small>
      <ProgressBar
        now={progress.percent}
        label={`${Math.round(progress.percent)}%`}
        className="mt-1"
      />
    </div>
  );
}
