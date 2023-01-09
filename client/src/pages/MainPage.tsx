import Carousel from '../components/carousel/Carousel';
import MainCarousel from '../components/carousel/MainCarousel';

const MainPage = () => {
  return (
    <div>
      <MainCarousel />
      <Carousel title="Notable collections" page="2" />
      <Carousel title="Top collector buys today" page="3" />
      <Carousel title="Photography NFT spotlight" page="4" />
    </div>
  );
};
export default MainPage;
