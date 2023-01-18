import customAxios from './axios';

export const logout = () => {
  return customAxios
    .get('/auth/logout', {
      headers: {
        RefreshToken: localStorage.getItem('REFRESH_TOKEN'),
      },
    })
    .then(() => window.localStorage.clear())
    .then(() => window.location.reload());
};

export const getMyProFile = async () => {
  return await customAxios.get('/api/members/mypage');
};

export const getUserProFile = async (memberId: string) => {
  return await customAxios.get(`/api/members/${memberId}`);
};
