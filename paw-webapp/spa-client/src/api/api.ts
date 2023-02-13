import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:6060',
  timeout: 5000,
  headers: {
    'Authorization': null,
  },
});

export const axiosPrivate = axios.create({
  baseURL:  'http://localhost:6060',
  timeout: 5000,
  headers: {
    'Authorization': null,
  },
});




export default instance;