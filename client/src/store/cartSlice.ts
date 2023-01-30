import { createSlice } from '@reduxjs/toolkit';

interface CartItemsType {
  cartItems: Array<{ itemId: number; coinName: string }>;
}

const initialState: CartItemsType = {
  cartItems: [],
};
const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    addTocart: (state, action) => {
      state.cartItems.push(action.payload);
    },
    removeCart: (state, action) => {
      state.cartItems = state.cartItems.filter(
        (el) => el.itemId !== action.payload
      );
    },
    clearCart: (state) => {
      state.cartItems = [];
    },
  },
});

export const { addTocart, removeCart, clearCart } = cartSlice.actions;
export default cartSlice.reducer;
