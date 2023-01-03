import axios from 'axios';
const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/vnd.user.v1+json',
        'Accept': 'application/vnd.user.v1+json',
        'Authorization': null,
    },
});

export default instance;