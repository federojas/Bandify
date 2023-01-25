import axios from 'axios';
const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
    headers: {
        'Authorization': null,
    },
});

export default instance;