import axios from "axios";

const BASE_URL = "http://localhost:8080/CourseWeb/";

export const endpoint = {
    "courses": "/course"
}

export default axios.create({
    baseURL: BASE_URL

});