import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import customAxios from '../utils/api/axios';

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
    }
    return res.data;
  }
);
interface Image {
  profileImage: string;
}
const initialState: Image = {
  profileImage: 'image',
};
const googleLoginSlice = createSlice({
  name: 'google',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(googleLogin.pending, () => {
        console.log('구글 로그인 중');
      })
      .addCase(googleLogin.fulfilled, (state, action) => {
        console.log('구글 로그인 성공');
        state.profileImage = action.payload.profileImage;
      })
      .addCase(googleLogin.rejected, () => {
        console.log('구글 로그인 실패');
      });
  },
});

export const googleLoginAction = googleLoginSlice.actions;
export default googleLoginSlice.reducer;
