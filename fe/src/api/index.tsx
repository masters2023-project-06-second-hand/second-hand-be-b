import axios from 'axios';
import { BASE_API_URL } from '../envConfig';
export const publicApi = axios.create({
  baseURL: `${BASE_API_URL}/api/`,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const privateApi = axios.create({
  baseURL: `${BASE_API_URL}/api`,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const multiFormApi = axios.create({
  baseURL: `${BASE_API_URL}/api`,
  headers: {
    'Content-Type': 'multipart/form-data',
  },
});

/** Todo
 * login 관련 api 될 때 작동 확인 될 때 수정하기
 * token 관련 로그아웃 및 확인 로직 추가하기
 * refresh token, 재발급 관련 추가 필요
 */
privateApi.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

multiFormApi.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
