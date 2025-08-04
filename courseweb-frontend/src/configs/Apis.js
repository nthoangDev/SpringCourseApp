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
    'myCourses': '/my-courses',

    "cart-items": "/secure/cart/unpaid",
    "cart-add": "/secure/cart/add",
    cartDelete: (itemId) => `/secure/cart/items/${itemId}`,
    "cart-clear": "/secure/cart/clear",
    "cart-total": "/secure/cart/total",

    "checkout": "/secure/cart/checkout",
    "vnpayReturn": "/payment/vnpay_return"

}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load("token")}`
    }
});

export default axios.create({
    baseURL: BASE_URL

});