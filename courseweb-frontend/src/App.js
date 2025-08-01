import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import "./App.css";
import CourseDetail from "./components/CourseDetail";
import { MyCartContext, MyUserContext } from "./configs/context";
import { useReducer } from "react";
import MyCartReducer from "./reducers/MyCartReducer";
import Cart from "./components/Cart";
import Register from "./components/Register";
import Login from "./components/Login";
import MyUserReducer from "./reducers/MyUserReducer";
import cookie from 'react-cookies';

function App() {
  let initialUser = cookie.load("user");
  let [cartCounter, cartDispatch] = useReducer(MyCartReducer, 0);
  let [user, dispatch] = useReducer(MyUserReducer, initialUser || null);
  return (
    <>
      <MyUserContext.Provider value={[user, dispatch]}>
        <MyCartContext.Provider value={[cartCounter, cartDispatch]}>
          <BrowserRouter>
            <Header />

            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/courses/:id" element={<CourseDetail />} />
              <Route path="/cart" element={<Cart />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
            </Routes>

            <Footer />
          </BrowserRouter>

        </MyCartContext.Provider>
      </MyUserContext.Provider>

    </>
  );
}

export default App;
