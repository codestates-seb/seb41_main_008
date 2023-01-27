import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

interface toastState {
  createColOpen: boolean;
  createItemOpen: boolean;
  removeColOpen: boolean;
  removeItemOpen: boolean;
  updateUserOpen: boolean;
  deleteUserOpen: boolean;
}

const initialState: toastState = {
  createColOpen: false,
  createItemOpen: false,
  removeColOpen: false,
  removeItemOpen: false,
  updateUserOpen: false,
  deleteUserOpen: false,
};

export const toastSlice = createSlice({
  name: 'toast',
  initialState,
  reducers: {
    setCreateColOpen: (state, action: PayloadAction<boolean>) => {
      state.createColOpen = action.payload;
    },
    setCreateItemOpen: (state, action: PayloadAction<boolean>) => {
      state.createItemOpen = action.payload;
    },
    setRemoveColOpen: (state, action: PayloadAction<boolean>) => {
      state.removeColOpen = action.payload;
    },
    setRemoveItemOpen: (state, action: PayloadAction<boolean>) => {
      state.removeItemOpen = action.payload;
    },
    setUpdateUserOpen: (state, action: PayloadAction<boolean>) => {
      state.updateUserOpen = action.payload;
    },
    setDeleteUserOpen: (state, action: PayloadAction<boolean>) => {
      state.deleteUserOpen = action.payload;
    },
  },
});

export const {
  setCreateColOpen,
  setCreateItemOpen,
  setRemoveColOpen,
  setRemoveItemOpen,
  setDeleteUserOpen,
  setUpdateUserOpen,
} = toastSlice.actions;

export default toastSlice.reducer;
