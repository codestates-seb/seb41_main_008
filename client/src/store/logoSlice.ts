import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

interface logoState {
  logoString: string;
}

const initialState: logoState = {
  logoString: '',
};

export const logoSlice = createSlice({
  name: 'logo',
  initialState,
  reducers: {
    setLogoString: (state, action: PayloadAction<string>) => {
      state.logoString = action.payload;
    },
  },
});

export const { setLogoString } = logoSlice.actions;

export default logoSlice.reducer;
