import customAxios from './axios';
import { refreshToken } from 'utils/token';
export const logout = () => {
  return customAxios
    .get('/auth/logout', {
      headers: {
        RefreshToken: refreshToken,
      },
    })
    .then(() => window.localStorage.clear())
    .then(() => window.location.reload());
};
