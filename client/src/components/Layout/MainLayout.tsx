// import Header from '../Header/Header';
import { Outlet } from 'react-router-dom';
import BuyCoinModal from 'components/CartingModal/BuyCoinModal';
import SellModal from 'components/CartingModal/SellModal';
const MainLayout = () => {
  return (
    <>
      {/* <Header /> */}
      <BuyCoinModal />
      <SellModal />
      <Outlet />
    </>
  );
};
/**App컴포넌트에서  MainLayout 안에 감싸진 컴포넌트들은 Outlet으로 표시된다*/
export default MainLayout;
