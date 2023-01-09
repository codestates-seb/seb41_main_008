import Footer from './components/Layout/Footer';
import Header from './components/header/Header';
import Carousel from './components/Carousel';
import ViewAll from './components/ViewAll';
import './App.css';

function App() {
  return (
    <div className="App">
      <Header />
      <ViewAll />
      <Carousel />
      <Footer />
    </div>
  );
}

export default App;
