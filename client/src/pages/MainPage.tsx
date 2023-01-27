import Footer from 'components/Layout/Footer';
import Top from '../components/Trending/Top';
import MainCarousel from 'components/Carousel/MainCarousel';
import Carousel from 'components/Carousel/Carousel';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setDeleteUserOpen } from 'store/toastSlice';
import { BsCheckCircleFill } from 'react-icons/bs';
import Notification from 'components/Notification';
import { useEffect } from 'react';
import MainHeader from 'components/Header/MainHeader';

const MainPage = () => {
  const deleteUserOpen = useAppSelector((state) => state.toast.deleteUserOpen);
  const dispatch = useAppDispatch();

  useEffect(() => {
    setTimeout(() => dispatch(setDeleteUserOpen(false)), 5000);
  }, [dispatch]);
  // bg-gradient-to-r from-rose-100 to-teal-100
  // via-yellow-100 from-red-200
  // bg-gradient-to-b from-indigo-200 via-red-200 to-yellow-100
  return (
    <div>
      <div className="bg-gradient-to-b via-yellow-100 from-red-200">
        <MainHeader />
        <MainCarousel />
      </div>
      <Top />
      <Carousel title="Notable collections" page="3" />
      <Footer />
      <Notification open={deleteUserOpen} setOpen={setDeleteUserOpen}>
        <p className="flex items-center gap-1 text-emerald-700">
          <span>
            <BsCheckCircleFill className="h-7 w-7" />
          </span>{' '}
          Deleted!
        </p>
      </Notification>
    </div>
  );
};
export default MainPage;
