import Footer from 'components/Layout/Footer';
import MainCarousel from 'components/Carousel/MainCarousel';
import Carousel from 'components/Carousel/Carousel';
import Top from 'components/Trending/Top';
import Header from 'components/Header/Header';

const MainPage = () => {
  return (
    <div className="dark:bg-[#262b2e] dark:text-white">
      <div className="bg-gradient-to-b via-yellow-100 from-red-200 dark:bg-gradient-to-b dark:via-neutral-500 dark:from-black">
        <Header />
        <MainCarousel />
      </div>
      <Top />
      <Carousel />
      <Footer />
    </div>
  );
};
export default MainPage;
