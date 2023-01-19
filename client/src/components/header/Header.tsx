import styled from 'styled-components';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../hooks/hooks';
import { openModal } from '../../store/modalSlice';
import {
  faBars,
  faXmark,
  faCartShopping,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { logout, getMyProFile } from 'utils/api/api';
import MobileDropdownList from './MobileDropdownLIst';
import MobileDropdown from './MobileDropdown';
import Dropdown from './Dropdown';

const SearchInput = styled.input`
  display: flex;
  border: 1px solid black;
  width: 100%;
  padding: 8px;
  border-radius: 10px;
  border: none;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
`;
const Header = () => {
  const profileImage = useAppSelector((state) => state.login.profileImage);
  const { isLogin } = useAppSelector((state) => state.login);
  console.log('isLogin', isLogin);
  console.log('profileImage', profileImage);
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [visible, setVisible] = useState(false);
  const visibleHandler = () => {
    setVisible(!visible);
  };
  const goToMypage = () => {
    getMyProFile().then((res: any) => {
      navigate(`/account/${res.data.member.memberId}`);
    });
  };
  return (
    <header className="flex justify-center  items-center font-bold z-50 p-4 sticky top-0 border-b-2 bg-white text-lg">
      <div className="flex gap-2 mr-2">
        <Link to={'/'}>logo</Link>
        <Link to={'/'}>NFTeam</Link>
      </div>
      <div className="w-full">
        <SearchInput placeholder="Search items, collections, and accoutns..." />
      </div>
      <Dropdown />
      <nav className="cursor-pointer">
        <ul className="flex gap-5 items-center">
          {isLogin ? (
            <button onClick={goToMypage}>
              <div className="w-8 h-8 ">
                <img
                  className="object-cover w-full h-full rounded-full"
                  src={`${profileImage}`}
                  alt=""
                />
              </div>
            </button>
          ) : (
            <Link to={'/signup'} className="p-2 max-[640px]:hidden">
              Signup
            </Link>
          )}
          {isLogin ? (
            <button onClick={logout}>Logout</button>
          ) : (
            <Link to={'/login'} className="p-2 max-[640px]:hidden">
              <span>Login</span>
            </Link>
          )}
          <button className="p-2" onClick={() => dispatch(openModal())}>
            <FontAwesomeIcon icon={faCartShopping} />
          </button>

          <button className="hidden max-[1040px]:flex" onClick={visibleHandler}>
            {visible ? (
              <FontAwesomeIcon icon={faXmark} />
            ) : (
              <FontAwesomeIcon icon={faBars} />
            )}
          </button>
        </ul>
        <MobileDropdown visible={visible}>
          <MobileDropdownList />
        </MobileDropdown>
      </nav>
    </header>
  );
};

export default Header;
