import Footer from 'components/Layout/Footer';
import Top from '../components/Trending/Top';
import MainCarousel from 'components/Carousel/MainCarousel';
import Carousel from 'components/Carousel/Carousel';

const MainPage = () => {
  return (
    <div>
      <MainCarousel />
      <Top />
      <Carousel title="Notable collections" page="3" />
      <Footer />
    </div>
  );
};
export default MainPage;
