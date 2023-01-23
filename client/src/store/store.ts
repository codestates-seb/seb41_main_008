import { configureStore, combineReducers } from '@reduxjs/toolkit';
import modalSlice from './modalSlice';
import loginSlice from './loginSlice';
import signupSlice from './signupSlice';
import cartSlice from './cartSlice';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import toastReducer from './toastSlice';

const persistConfig = {
  key: 'root',
  storage,
  blacklist: ['signup', 'logo', 'banner'],
};
const reducer = combineReducers({
  modal: modalSlice,
  login: loginSlice,
  signup: signupSlice,
  toast: toastReducer,
  cart: cartSlice,
});
const persistedReducer = persistReducer(persistConfig, reducer);
export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
