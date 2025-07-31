import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <>
      <BrowserRouter>
        <Header/>
          
          <Routes>
            <Route path="/" element={<Home/>}/>
          </Routes>

        <Footer/>
      </BrowserRouter>


    </>
  );
}

export default App;
