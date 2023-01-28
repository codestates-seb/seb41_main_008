import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import customAxios from '../utils/api/axios';

interface LoginProps {
  isFetching: boolean;
  isLogin: boolean;
  profileImage?: string;
}
/**axios response 타입 any로 한것들 공부해서 타입수정필요 */
export const login = createAsyncThunk(
  'login/login',
  async (data: {}, thunkAPI): Promise<any> => {
    try {
      const res: any = await customAxios.post('/auth/login', data);
      console.log(res);
      if (res.headers) {
        localStorage.setItem('ACCESS_TOKEN', res.headers.authorization);
        localStorage.setItem('REFRESH_TOKEN', res.headers.refreshtoken);
        localStorage.setItem('MEMBER_ID', res.data.id);
        localStorage.setItem('CART_ID', res.data.cartId);
        // localStorage.setItem('CART_ITEMS', JSON.stringify(res.data.cart.items));
      }
      console.log(res);
      return res.data;
    } catch (error: any) {
      console.log(error);
      return thunkAPI.rejectWithValue(error.response);
    }
  }
);
export const googleLogin = createAsyncThunk(
  'login/googleLogin',
  async (token: string) => {
    const res: any = await customAxios.get(`/auth/login/google`, {
      headers: {
        googleToken: token,
      },
    });
    console.log(res);
    if (res.headers) {
      localStorage.setItem('ACCESS_TOKEN', res.headers.authorization);
      localStorage.setItem('REFRESH_TOKEN', res.headers.refreshtoken);
      localStorage.setItem('MEMBER_ID', res.data.id);
      localStorage.setItem('CART_ID', res.data.cart.cartId);
    }
    return res.data;
  }
);
/**구글 로그인 타입 추가 */
const initialState: LoginProps = {
  isLogin: false,
  isFetching: false,
  profileImage: '',
};

const loginSlice = createSlice({
  name: 'login',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(login.pending, (state) => {
        console.log('로그인 중');
        state.isFetching = true;
      })
      .addCase(login.fulfilled, (state) => {
        console.log('로그인 성공');
        state.isFetching = false;
        state.isLogin = true;
      })
      .addCase(login.rejected, (state) => {
        console.log('로그인 실패');
        state.isFetching = false;
      });
    builder
      .addCase(googleLogin.pending, () => {
        console.log('구글 로그인 중');
      })
      .addCase(googleLogin.fulfilled, (state, action) => {
        console.log('구글 로그인 성공');
        state.isLogin = true;
        state.profileImage = action.payload.profileImageName;
      })
      .addCase(googleLogin.rejected, () => {
        console.log('구글 로그인 실패');
      });
  },
});

export const loginAction = loginSlice.actions;

export default loginSlice.reducer;
