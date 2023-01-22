import styled from 'styled-components';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAppSelector } from '../../hooks/hooks';
import { faBars, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
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
  const { cartItems } = useAppSelector((state) => state.cart);
  const [visible, setVisible] = useState(false);
  const visibleHandler = () => {
    setVisible(!visible);
  };
  console.log(cartItems);
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
      <nav>
        <ul className="flex gap-5 items-center">
          <button
            className="hidden  max-[1040px]:flex"
            onClick={visibleHandler}
          >
            {visible ? (
              <FontAwesomeIcon icon={faXmark} />
            ) : (
              <FontAwesomeIcon icon={faBars} />
            )}
          </button>
        </ul>
        <MobileDropdown visible={visible} setVisible={setVisible}>
          <MobileDropdownList />
        </MobileDropdown>
      </nav>
    </header>
  );
};

export default Header;
