// import Header from '../Header/Header';
import { Outlet } from 'react-router-dom';
import BuyCoinModal from 'components/Modals/BuyCoinModal';
import SellModal from 'components/Modals/SellModal';
import WalletModal from 'components/Modals/WalletModal';
import CartingModal from 'components/Modals/CartingModal';
import TransActionModal from '../Modals/TransActionModal';
const MainLayout = () => {
  return (
    <>
      <Header />
      <CartingModal />
      <BuyCoinModal />
      <SellModal />
      <WalletModal />
      <TransActionModal />
      <Outlet />
    </>
  );
};
/**App컴포넌트에서  MainLayout 안에 감싸진 컴포넌트들은 Outlet으로 표시된다*/
export default MainLayout;
