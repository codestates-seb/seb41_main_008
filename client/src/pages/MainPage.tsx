import Carousel from 'components/carousel/Carousel';
import MainCarousel from 'components/carousel/MainCarousel';
import Footer from 'components/Layout/Footer';
import Top from '../components/Trending/Top';


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
