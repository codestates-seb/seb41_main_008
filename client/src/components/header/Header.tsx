import styled from 'styled-components';
import Dropdown from './Dropdown';
import MobileDropdown from './MobileDropdown';
import MobileDropdownList from './MobileDropdownLIst';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../hooks/hooks';
import { openModal } from '../../store/modalSlice';
import {
  faBars,
  faXmark,
  faCartShopping,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { accessToken } from '../../utils/token';
import { logout } from 'utils/api/api';
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
  const profileImage = useAppSelector((state) => state.google?.profileImage);
  console.log('헤더구글이미지', profileImage);
  console.log(accessToken);
  const dispatch = useAppDispatch();
  const [visible, setVisible] = useState(false);
  const visibleHandler = () => {
    setVisible(!visible);
  };
  return (
    <header className="flex justify-center  items-center font-bold z-50 p-4 sticky top-0 border-b-2 bg-white text-lg">
      <div className="flex gap-2 mr-2">
        <div>logo</div>
        <div>NFTeam</div>
      </div>
      <div className="w-full">
        <SearchInput placeholder="Search items, collections, and accoutns..." />
      </div>
      <Dropdown />
      <nav className="cursor-pointer">
        <ul className="flex gap-5 items-center">
          {accessToken ? (
            <Link to={'/account'}>
              <div className="w-8 h-8 ">
                <img
                  className="object-cover w-full h-full rounded-full"
                  src={`${profileImage}`}
                  alt=""
                />
              </div>
            </Link>
          ) : (
            <Link to={'/signup'} className="p-2 max-[640px]:hidden">
              Signup
            </Link>
          )}
          {accessToken ? (
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
