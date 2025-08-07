import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import "./App.css";
import CourseDetail from "./components/CourseDetail";
import { MyCartContext, MyUserContext, MyNotificationContext } from "./configs/context";
import { useReducer } from "react";
import MyCartReducer from "./reducers/MyCartReducer";
import MyNotificationReducer from "./reducers/MyNotificationReducer";
import Cart from "./components/Cart";
import Register from "./components/Register";
import Login from "./components/Login";
import MyUserReducer from "./reducers/MyUserReducer";
import cookie from 'react-cookies';
import MyCourses from "./components/MyCourses";
import TeacherClasses from "./components/TeacherClasses";
import GradeDashboard from "./components/GradeDashboard";
import UserAssessmentResult from "./components/UserAssessmentResult";

function App() {
  const initialUser = cookie.load("user");
  const [cartCounter, cartDispatch]         = useReducer(MyCartReducer, 0);
  const [user, dispatchUser]                = useReducer(MyUserReducer, initialUser || null);
  const [reminderCount, reminderDispatch]   = useReducer(MyNotificationReducer, 0);

  return (
    <MyUserContext.Provider value={[user, dispatchUser]}>
      <MyCartContext.Provider value={[cartCounter, cartDispatch]}>
        <MyNotificationContext.Provider value={[reminderCount, reminderDispatch]}>
          <BrowserRouter>
            <Header />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/courses/:id" element={<CourseDetail />} />
              <Route path="/cart" element={<Cart />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/my-courses" element={<MyCourses />} />
              <Route path="/grade" element={<TeacherClasses />} />
              <Route path="/grade/:courseId" element={<GradeDashboard />} />
              <Route path="/result/:courseId" element={<GradeDashboard />} />
              <Route path="/result/:assessmentId/:userId" element={<UserAssessmentResult />} />
            </Routes>
            <Footer />
          </BrowserRouter>
        </MyNotificationContext.Provider>
      </MyCartContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;
