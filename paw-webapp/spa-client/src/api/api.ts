import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://pawserver.it.itba.edu.ar/paw-2022a-03/',
  timeout: 5000,
  headers: {
    'Authorization': null,
  },
});

export const axiosPrivate = axios.create({
  baseURL: 'http://pawserver.it.itba.edu.ar/paw-2022a-03/',
  timeout: 5000,
  headers: {
    'Authorization': null,
  },
});

export default instance;