import { BrowserRouter, Routes, Route } from 'react-router-dom';

import LoginPage from '../src/pages/LoginPage';
import SignupPage from '../src/pages/SignupPage';
import MainPage from '../src/pages/MainPage';
import MainLayout from '../src/components/Layout/MainLayout';
import MyCollectionPage from 'pages/MyCollectionPage';
import AccountPage from 'pages/AccountPage';
import CreateCollectionPage from 'pages/CreateCollectionPage';
import MissingPage from 'components/CreateCollection/MissingPage/MissingPage';
import CollectionDetails from 'pages/CollectionDetails';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route element={<MainLayout />}>
            <Route path="/" element={<MainPage />} />
            <Route path="/collections" element={<MyCollectionPage />} />
            <Route
              path="/collection/create"
              element={<CreateCollectionPage />}
            />
            <Route path="collection/:id" element={<CollectionDetails />} />
            <Route path="*" element={<MissingPage />} />
            <Route path="/account" element={<AccountPage />} />
          </Route>

          <Route path="login" element={<LoginPage />} />
          <Route path="signup" element={<SignupPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}
/**MainLayout 컴포넌트에는 전역에 보여주고싶은 컴포넌트들이 들어있다 ex)header,cartModal */
/**MainLayout 에 감싸지는 페이지들은 MainLayout이 가지고있는 컴포넌트들을 다 보여주며 밖에있는 로그인,회원가입페이지는 header,footer등이 안보이게된다. */
export default App;
