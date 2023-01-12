import { configureStore } from '@reduxjs/toolkit';
import modalSlice from './modalSlice';
import loginSlice from './loginSlice';
import signupSlice from './signupSlice';
import logoReducer from './logoSlice';
import bannerReducer from './bannerSlice';

const store = configureStore({
  reducer: {
    modal: modalSlice,
    login: loginSlice,
    signup: signupSlice,
    logo: logoReducer,
    banner: bannerReducer,
  },
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
