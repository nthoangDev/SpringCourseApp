import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = "http://localhost:8080/CourseWeb/api/";

export const endpoint = {
    "courses": "/courses",
    courseDetail: (id) => `/courses/${id}`,
    "tags": "/tags",
    'register': '/users',
    'login': '/login',
    'profile': '/secure/profile',
    'myCourses':'/my-courses'

}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers:{
        'Authorization': `Bearer ${cookie.load("token")}`
    }
});

export default axios.create({
    baseURL: BASE_URL

});