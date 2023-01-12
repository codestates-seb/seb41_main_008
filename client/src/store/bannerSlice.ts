import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';
import { RootState } from './store';

interface bannerState {
  url: string;
}

const initialState: bannerState = {
  url: '',
};

export const bannerSlice = createSlice({
  name: 'banner',
  initialState,
  reducers: {
    setBanner: (state, action: PayloadAction<string>) => {
      state.url = action.payload;
    },
  },
});

export const { setBanner } = bannerSlice.actions;

export const selectLogo = (state: RootState) => state.logo.url;

export default bannerSlice.reducer;
