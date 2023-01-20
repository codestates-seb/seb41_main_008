import { createSlice, current } from '@reduxjs/toolkit';

interface test {
  cartItems: Array<{}>;
}
const initialState: test = {
  cartItems: [],
};
const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    addTocart: (state, action) => {
      console.log(action);
      console.log(current(state.cartItems));
      // state.cartItems.push(action.payload);
      if (Array.isArray(action.payload) === true) {
        action.payload.map((el: any) => {
          return state.cartItems.push(el);
        });
      } else {
        state.cartItems.push(action.payload);
      }
      // state.cartItems.push(action.payload);
    },
  },
});

export const { addTocart } = cartSlice.actions;
export default cartSlice.reducer;
