import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

interface toastState {
  open: boolean;
  cartOpen: boolean;
}

const initialState: toastState = {
  open: false,
  cartOpen: false,
};

export const toastSlice = createSlice({
  name: 'toast',
  initialState,
  reducers: {
    setOpen: (state, action: PayloadAction<boolean>) => {
      state.open = action.payload;
    },
    cartOpen: (state, action) => {
      state.cartOpen = action.payload;
    },
  },
});

export const { setOpen, cartOpen } = toastSlice.actions;

export default toastSlice.reducer;
