import axios from 'axios';

export const logout = () => {
  return axios
    .get(
      'http://ec2-3-35-204-189.ap-northeast-2.compute.amazonaws.com/auth/logout'
    )
    .then(() => window.localStorage.clear());
};
