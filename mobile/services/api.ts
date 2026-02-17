import axios from 'axios';

const api = axios.create({
  // Substitua pelo IP da sua máquina (veja no ipconfig)
  baseURL: 'http://192.168.0.81:8080',
});

export default api;