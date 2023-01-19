import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

interface bannerState {
  bannerString: string;
}

const initialState: bannerState = {
  bannerString: '',
};

export const bannerSlice = createSlice({
  name: 'banner',
  initialState,
  reducers: {
    setBannerString: (state, action: PayloadAction<string>) => {
      state.bannerString = action.payload;
    },
  },
});

export const { setBannerString } = bannerSlice.actions;

export default bannerSlice.reducer;
