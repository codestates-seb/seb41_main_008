/* eslint-disable */
import Footer from 'components/Layout/Footer';
import MainCarousel from 'components/Carousel/MainCarousel';
import Carousel from 'components/Carousel/Carousel';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setDeleteUserOpen } from 'store/toastSlice';
import { BsCheckCircleFill } from 'react-icons/bs';
import Notification from 'components/Notification';
import { useEffect } from 'react';
import Top from 'components/Trending/Top';
import Header from 'components/Header/Header';

const MainPage = () => {
  const deleteUserOpen = useAppSelector((state) => state.toast.deleteUserOpen);
  const dispatch = useAppDispatch();
  useEffect(() => {
    setTimeout(() => dispatch(setDeleteUserOpen(false)), 5000);
  }, [dispatch]);

  return (
    <div className="dark:bg-[#262b2e] dark:text-white">
      <div className="bg-gradient-to-b via-yellow-100 from-red-200 dark:bg-gradient-to-b dark:via-neutral-500 dark:from-black">
        <Header />
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
