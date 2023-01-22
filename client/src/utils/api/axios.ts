import axios from 'axios';
const baseURL = process.env.REACT_APP_API_URL;
const customAxios = axios.create({
  baseURL,
  withCredentials: true,
});
/** any타입 수정필요 */
/** interceptors.requset 란 서버에 요청전송 직전에 가로채어 요청을 수정 힐수있게해준다 */
customAxios.interceptors.request.use(
  (config: any) => {
    const accessToken = localStorage.getItem('ACCESS_TOKEN');
    if (accessToken) {
      config.headers['Authorization'] = `${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/** interceptors.response 란 서버로부터 받은 응답을 프론트단에 리턴하기전 가로채어 수정할수있게해준다. */
customAxios.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const { config } = error;
    if (error.response.status === 403) {
      try {
        const res: any = await axios.get(`${baseURL}/auth/reissue`, {
          headers: {
            RefreshToken: localStorage.getItem('REFRESH_TOKEN'),
          },
        });
        if (res) {
          localStorage.setItem('ACCESS_TOKEN', res.headers.authorization);
          return await customAxios.request(config);
        }
      } catch (error: any) {
        if (error.response.status === 400) {
          alert('로그인 유지기간이 만료되었습니다. 다시 로그인 해주세요.');
          localStorage.clear();
          window.location.replace('/login');
        }
        console.log(error);
      }
    }
    return Promise.reject(error);
  }
);
export default customAxios;
