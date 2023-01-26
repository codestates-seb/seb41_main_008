import { createSlice } from '@reduxjs/toolkit';
import type { RootState } from './store';

interface ModalState {
  itemId: number;
  isOpen: boolean;
  sellOpen: boolean;
  paymentOpen: boolean;
  walletOpen: boolean;
}
const initialState = {
  isOpen: false,
  sellOpen: false,
  paymentOpen: false,
  walletOpen: false,
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
    openPayment: (state) => {
      state.paymentOpen = true;
    },
    closePayment: (state) => {
      state.paymentOpen = false;
    },
    openWallet: (state) => {
      state.walletOpen = true;
    },
    closeWallet: (state) => {
      state.walletOpen = false;
    },
  },
});
export const {
  openModal,
  closeModal,
  openSell,
  closeSell,
  openPayment,
  closePayment,
  openWallet,
  closeWallet,
} = modalSlice.actions;
export const viewModal = (state: RootState) => state.modal;
export default modalSlice.reducer;
