import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

interface toastState {
  open: boolean;
}

const initialState: toastState = {
  open: false,
};

export const toastSlice = createSlice({
  name: 'toast',
  initialState,
  reducers: {
    setOpen: (state, action: PayloadAction<boolean>) => {
      state.open = action.payload;
    },
  },
});

export const { setOpen } = toastSlice.actions;

export default toastSlice.reducer;
