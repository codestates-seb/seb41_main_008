import Footer from 'components/Layout/Footer';
import Top from '../components/Trending/Top';
import Carousel from 'components/Carousel/Carousel';
import MainCarousel from 'components/Carousel/MainCarousel';

const MainPage = () => {
  return (
    <div>
      <MainCarousel />
      <Top />
      <Carousel title="Notable collections" page="3" />
      <Carousel title="Top collector buys today" page="5" />
      <Carousel title="Photography NFT spotlight" page="7" />
      <Footer />
    </div>
  );
};
export default MainPage;
