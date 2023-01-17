import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import customAxios from '../utils/api/axios';

interface LoginProps {
  email: string;
  password: string;
  isFetching: boolean;
}
/**axios response 타입 any로 한것들 공부해서 타입수정필요 */
export const login = createAsyncThunk(
  'login/login',
  async (data: {}, thunkAPI): Promise<any> => {
    try {
      const res: any = await customAxios.post('/auth/login', data);
      if (res.headers) {
        localStorage.setItem('ACCESS_TOKEN', res.headers.authorization);
        localStorage.setItem('REFRESH_TOKEN', res.headers.refreshtoken);
      }
      console.log(res);
      return res.data;
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.response);
    }
  }
);

const initialState: LoginProps = {
  email: '',
  password: '',
  isFetching: false,
};

const loginSlice = createSlice({
  name: 'login',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(login.pending, (state) => {
        state.isFetching = true;
      })
      .addCase(login.fulfilled, (state, { payload }) => {
        state.email = payload.email;
        state.password = payload.password;
        state.isFetching = false;
        // state.accessToken = payload.headers.authorization;
      })
      .addCase(login.rejected, (state) => {
        state.isFetching = false;
      });
  },
});

export const loginAction = loginSlice.actions;

export default loginSlice.reducer;
