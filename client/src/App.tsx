import Carousel from './components/carousel/Carousel';
import Footer from './components/Layout/Footer';
import Header from './components/header/Header';
import MainCarousel from './components/carousel/MainCarousel';

function App() {
  return (
    <div className="App">
      <Header />
      <MainCarousel />
      <Carousel title="Notable collections" page="2" />
      <Carousel title="Top collector buys today" page="3" />
      <Carousel title="Photography NFT spotlight" page="4" />
      <Footer />
    </div>
  );
}

export default App;
