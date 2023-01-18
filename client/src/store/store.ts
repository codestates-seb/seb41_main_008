import { configureStore } from '@reduxjs/toolkit';
import modalSlice from './modalSlice';
import loginSlice from './loginSlice';
import signupSlice from './signupSlice';
import logoReducer from './logoSlice';
import bannerReducer from './bannerSlice';
import toastReducer from './toastSlice';

const store = configureStore({
  reducer: {
    modal: modalSlice,
    login: loginSlice,
    signup: signupSlice,
    logo: logoReducer,
    banner: bannerReducer,
    toast: toastReducer,
  },
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
