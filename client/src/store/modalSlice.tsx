import { createSlice } from '@reduxjs/toolkit';
import type { RootState } from './store';

interface ModalState {
  itemId: number;
  isOpen: boolean;
  sellOpen: boolean;
}
const initialState = {
  isOpen: false,
  sellOpen: false,
  itemId: 0,
} as ModalState;

const modalSlice = createSlice({
  name: 'modal',
  initialState,
  reducers: {
    openModal: (state) => {
      state.isOpen = true;
    },
    closeModal: (state) => {
      state.isOpen = false;
    },
    openSell: (state, action) => {
      state.itemId = action.payload;
      state.sellOpen = true;
    },
    closeSell: (state) => {
      state.sellOpen = false;
    },
  },
});
export const { openModal, closeModal, openSell, closeSell } =
  modalSlice.actions;
export const viewModal = (state: RootState) => state.modal;
export default modalSlice.reducer;
