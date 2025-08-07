import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = "http://localhost:8080/CourseWeb/api";

export const endpoint = {
  courses: "/courses",
  courseDetail: (id) => `/courses/${id}`,
  tags: "/tags",
  register: "/users",
  login: "/login",
  profile: "/secure/profile",
  myCourses: "/secure/my-courses",
  courseProgress: (courseId) => `/secure/courses/${courseId}/progress`,

  // Cart & payment
  "cart-items": "/secure/cart/unpaid",
  "cart-add": "/secure/cart/add",
  cartDelete: (itemId) => `/secure/cart/items/${itemId}`,
  "cart-clear": "/secure/cart/clear",
  "cart-total": "/secure/cart/total",
  // checkout: "/secure/cart/checkout",
  // vnpayReturn: "/payment/vnpay_return",


  // Notifications
  reminderCount: "/secure/notifications/reminder/count",
  notifications: "/secure/notifications",
  studyReminder: (courseId) => `/secure/notifications/study-reminder/${courseId}`,

  "checkout": "/secure/cart/checkout",
  "vnpayReturn": "/payment/vnpay_return",

  gradeAssessment: (aid, uid) => `/secure/assessments/${aid}/grade/${uid}`,
  userAssessmentResult: (aid, uid) => `/secure/assessments/${aid}/results/${uid}`,

  "teacher-classes": "/secure/teacher/courses",
  courseLessons: (id) => `/secure/courses/${id}/lessons`,
  courseStudents: (id) => `/secure/courses/${id}/students`,
  lessonAssessment: (id) => `/secure/lessons/${id}/assessments`,

  generateCertificate: (courseId) => `/secure/certificates/generate/${courseId}`,

};


// Tạo axios instance với Authorization
export const authApis = () =>
  axios.create({
    baseURL: BASE_URL,
    headers: {
      Authorization: `Bearer ${cookie.load("token")}`,
    },
  });

// Tạo instance axios mặc định
export default axios.create({
  baseURL: BASE_URL,
});
