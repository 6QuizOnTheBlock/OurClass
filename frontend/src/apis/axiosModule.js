import axios from "axios";

const instance = axios.create({
    // baseURL: 'http://localhost:8080/api',
    baseURL: 'https://k10d107.p.ssafy.io/api'
})

const tokens = JSON.parse(localStorage.getItem('tokens')) || {};

if(tokens.Authorization) {
    instance.defaults.headers.common["Authorization"] = tokens.Authorization;
}

export { instance }