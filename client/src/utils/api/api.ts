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

export const getItemsData = async (itemId: string | number | undefined) => {
  return await customAxios.get(`/api/items/${itemId}`);
};

export const cartSaveHandler = (data: {
  cartId: number;
  itemIdList: number[];
  totalPrice: number;
}) => {
  alert('d');
  return customAxios
    .post('/api/carts/save', data)
    .then((res) => console.log(res));
};

/**업비트 Open API */
export const getCoinPrice = async (coin: string | undefined) => {
  const options = { method: 'GET', headers: { accept: 'application/json' } };
  return await fetch(
    `https://api.upbit.com/v1/ticker?markets=krw-${coin}`,
    options
  ).then((res) => res.json());
};
