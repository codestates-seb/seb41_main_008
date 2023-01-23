import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { Provider } from 'react-redux';
import store from './store/store';
import { PersistGate } from 'redux-persist/integration/react';
import { persistStore } from 'redux-persist';
import CartingModal from 'components/CartingModal/CartingModal';
import SellModal from 'components/CartingModal/SellModal';
const persistor = persistStore(store);
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <CartingModal />
        <SellModal />
        <App />
      </PersistGate>
    </Provider>
  </React.StrictMode>
);
