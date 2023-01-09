import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface LoginProps {
  email: string;
  password: string;
  isFetching: boolean;
  accessToken: string;
}

export const login = createAsyncThunk(
  'login/login',
  async (data: {}, thunkAPI) => {
    try {
      const res = await axios.post(
        'http://ec2-3-35-204-189.ap-northeast-2.compute.amazonaws.com/api/members/login',
        data
      );
      return await res.data;
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.response);
    }
  }
);

const initialState: LoginProps = {
  email: '',
  password: '',
  isFetching: false,
  accessToken: '',
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
