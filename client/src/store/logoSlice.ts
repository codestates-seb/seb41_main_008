import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';
import { RootState } from './store';

interface logoState {
  url: string;
}

const initialState: logoState = {
  url: '',
};

export const logoSlice = createSlice({
  name: 'logo',
  initialState,
  reducers: {
    setLogo: (state, action: PayloadAction<string>) => {
      state.url = action.payload;
    },
  },
});

export const { setLogo } = logoSlice.actions;

export const selectLogo = (state: RootState) => state.logo.url;

export default logoSlice.reducer;
