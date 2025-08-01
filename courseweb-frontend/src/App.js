import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import "./App.css";
import CourseDetail from "./components/CourseDetail";
function App() {
  return (
    <>
      <BrowserRouter>
        <Header/>
          
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route  path="/courses/:id" element={<CourseDetail/>} />
          </Routes>

        <Footer/>
      </BrowserRouter>


    </>
  );
}

export default App;
