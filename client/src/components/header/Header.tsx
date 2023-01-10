import styled from 'styled-components';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAppDispatch } from '../../hooks/hooks';
import { openModal } from '../../store/modalSlice';
import {
  faBars,
  faXmark,
  faCartShopping,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import MobileDropdown from './MobileDropdown';
import MobileDropdownList from './MobileDropdownLIst';
import { accessToken } from '../../utils/token';
import { logout } from 'utils/api/api';
import DropdownItems from './DropdownItems';

const SearchInput = styled.input`
  display: flex;
  border: 1px solid black;
  width: 100%;
  padding: 8px;
  border-radius: 10px;
  border: none;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
`;

const DropdownList = styled.li`
  position: relative;
  padding-top: 8px;
  padding-bottom: 8px;
  div {
    top: 45px;
    visibility: hidden;
    transition: 0.3s all;
    opacity: 0;
  }
  &:hover {
    div {
      top: 50px;
      visibility: visible;
      opacity: 1;
    }
  }
`;

const Header = () => {
  const dispatch = useAppDispatch();
  const [visible, setVisible] = useState(false);
  const visibleHandler = () => {
    setVisible(!visible);
  };
  /**로그아웃 함수같은경우 임시로 만든것이며 로그아웃 api가 만들어진다면 따로 utils에서 export하여 쓰는것이 편할것같음. */

  return (
    <header className="flex justify-center items-center font-bold z-50 p-4 sticky top-0 border-b-2 bg-white">
      <div className="flex gap-2 mr-2">
        <div>logo</div>
        <div>NFTeam</div>
      </div>
      <div className="w-full">
        <SearchInput placeholder="Search items, collections, and accoutns..." />
      </div>
      <nav className="ml-2 cursor-pointer">
        <ul className="flex items-center gap-5 mr-8 ml-2 max-[1040px]:hidden">
          <DropdownList>
            <span>Explore</span>
            <DropdownItems />
          </DropdownList>
          <DropdownList>
            <span>MENU1</span>
            <DropdownItems />
          </DropdownList>
          <DropdownList>
            <span>MENU2</span>
            <DropdownItems />
          </DropdownList>
        </ul>
      </nav>
      <nav className="cursor-pointer">
        <ul className="flex gap-5 items-center">
          {accessToken ? (
            <Link to={'/mypage'}>
              <div className="w-8 h-8 ">
                <img
                  className="object-cover w-full h-full rounded-full"
                  src="https://i.seadn.io/gae/4_jjlcK-9NHUw0dlYseaQt1g5bKjgBuxvYMxrrFuArSBe-pbVxaLUDHqnio4hNgVBQUPpwMwOVDMnikYBLOmY5nfkj2vXlEHxKbzBXk?auto=format&w=1000"
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
            <FontAwesomeIcon icon={faCartShopping} size={'xl'} />
          </button>

          <button className="hidden max-[640px]:flex" onClick={visibleHandler}>
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
