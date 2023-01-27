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

/**카트정보 저장 api */
export const cartSaveHandler = (data: {
  cartId: number;
  itemIdList: number[];
  totalPrice: number;
}) => {
  alert('결제로진행~');
  console.log(data);
  return customAxios
    .post('/api/carts/save', data)
    .then((res) => console.log(res));
};
/**판매하기 api */
export const sellItemHandler = async (
  itemId: number,
  data: { itemPrice: number }
) => {
  await customAxios
    .post(`/api/items/sell/${itemId}`, data)
    .then((res) => console.log(res));
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
  return await customAxios.get(
    `/api/coins/approve?pg_token=${pgToken}&tid=${tid}`
  );
};

/**업비트 Open API */
export const getCoinPrice = async (coin: string | undefined) => {
  const options = { method: 'GET', headers: { accept: 'application/json' } };
  return await fetch(
    `https://api.upbit.com/v1/ticker?markets=krw-${coin}`,
    options
  ).then((res) => res.json());
};
