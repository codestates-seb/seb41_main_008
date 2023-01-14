import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import customAxios from '../utils/api/axios';

interface SignupProps {
  nickname: string;
  email: string;
  password: string;
  isFetching: boolean;
}

export const signup = createAsyncThunk(
  'signup/signup',
  async (data: {}, thunkAPI) => {
    console.log(data);
    try {
      const res = await customAxios.post('/api/members', data);
      return res.data;
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.response);
    }
  }
);

const initialState: SignupProps = {
  nickname: '',
  email: '',
  password: '',
  isFetching: false,
};

const signupSlice = createSlice({
  name: 'signup',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(signup.pending, (state) => {
        state.isFetching = true;
      })
      .addCase(signup.fulfilled, (state, { payload }) => {
        state.nickname = payload.nickname;
        state.email = payload.email;
        state.password = payload.password;
        state.isFetching = false;
      })
      .addCase(signup.rejected, (state) => {
        state.isFetching = false;
      });
  },
});

export const loginAction = signupSlice.actions;
export default signupSlice.reducer;
