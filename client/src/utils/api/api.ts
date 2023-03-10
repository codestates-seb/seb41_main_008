import customAxios from './axios';
import axios from 'axios';
export const logout = () => {
  return customAxios
    .get('/auth/logout', {
      headers: {
        RefreshToken: localStorage.getItem('REFRESH_TOKEN'),
      },
    })
    .then(() => {
      window.localStorage.clear();
      window.location.replace('/');
    });
};

export const getMyProFile = async () => {
  return await customAxios.get('/api/members/mypage');
};

export const getUserProFile = async (memberId: string) => {
  return await customAxios.get(`/api/members/${memberId}`);
};

export const getItemsData = async (itemId: number) => {
  return await customAxios.get(`/api/items/${itemId}`);
};

export const getRaingkingData = async (time: string | number | undefined) => {
  return await customAxios.get(`/api/ranking/time/${time}`);
};

export const getCoinData = async (coinId: string | number | undefined) => {
  return await customAxios.get(`/api/ranking/time/${coinId}`);
};

export const getSearchdata = async (
  keyword: string | number | undefined,
  page: string | number | undefined,
  size: string | number | undefined
) => {
  return await customAxios.get(
    `/api/search?keyword=${keyword}&page=${page}&size=${size}`
  );
};

/**카트정보 저장 api */
export const cartSaveHandler = async (data: {
  cartId: number;
  itemIdList: number[];
  totalPrice: number;
}) => {
  return await customAxios.post('/api/carts/save', data);
};
/**판매하기 api */
export const sellItemHandler = async (
  itemId: number,
  data: { itemPrice: number }
) => {
  await customAxios
    .post(`/api/items/sell/${itemId}`, data)
    .catch((err) => console.log(err));
};

/**TransAction api */
export const transAction = async (data: any) => {
  return await customAxios.post('/api/trans', data);
};

/**지갑 잔고 확인 api */
export const getMyCoin = async () => {
  return await customAxios.get('/api/coins/my');
};
/**코인 구매호출 api */
export const buyCoin = async (data: any) => {
  return await customAxios.post('/api/coins/purchase', data);
};

/**카카오페이 api */
export const kakaoPay = async (pgToken: string, tid: string | null) => {
  if (!pgToken || !tid) return;
  return await customAxios.get(
    `/api/coins/approve?pg_token=${pgToken}&tid=${tid}`
  );
};

/**코인 주문정보 조회 */
export const getCoinOrderInfo = async (tid?: string | null) => {
  return await customAxios.get(`/api/coins/success?tid=${tid}`);
};

/**업비트 Open API */
export const getCoinPrice = async (coin: string | undefined) => {
  if (coin === undefined) return;

  return await axios.get(`https://api.upbit.com/v1/ticker?markets=krw-${coin}`);
};
