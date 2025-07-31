import axios from "axios";

const BASE_URL = "http://localhost:8080/CourseWeb/api/";

export const endpoint = {
    "courses": "/courses",
    "tags": "/tags"
}

export default axios.create({
    baseURL: BASE_URL

});