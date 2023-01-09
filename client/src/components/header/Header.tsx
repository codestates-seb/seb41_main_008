import styled from 'styled-components';
import DropdownItems from '../Header/DropdownItems';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAppDispatch } from '../../hooks/hooks';
import { openModal } from '../../store/modalSlice';
import { faBars, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import MobileDropdown from './MobileDropdown';
import MobileDropdownList from './MobileDropdownLIst';
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
  return (
    <header className="flex justify-center items-center border-2 p-4 sticky top-0  ">
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
          <Link to={'/signup'} className="p-2 max-[640px]:hidden">
            Signup
          </Link>
          <Link to={'/login'} className="p-2 max-[640px]:hidden">
            Login
          </Link>
          <button className="p-2" onClick={() => dispatch(openModal())}>
            cart
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
