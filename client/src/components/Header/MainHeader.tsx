import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { faBars, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import MobileDropdownList from './MobileDropdownLIst';
import MobileDropdown from './MobileDropdown';
import MainDropdown from './MainDropdown';
// import { useAppSelector } from '../../hooks/hooks';

const SearchInput = styled.input`
  display: flex;
  width: 100%;
  padding: 8px;
  border-radius: 10px;
  outline: none;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
`;
const MainHeader = () => {
  const [isScrolled, setIsScrolled] = useState(false);
  // const { cartItems } = useAppSelector((state) => state.cart);
  const [visible, setVisible] = useState(false);
  const visibleHandler = () => {
    setVisible(!visible);
  };

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 0) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <header
      className={`duration-300 flex justify-center items-center font-bold z-20 p-4 fixed top-0 left-0 right-0 text-lg ${
        isScrolled && 'bg-white'
      }`}
    >
      <div className="flex gap-2 mr-2">
        <Link to={'/'}>logo</Link>
        <Link className={`${!isScrolled && 'text-white'}`} to={'/'}>
          NFTeam
        </Link>
      </div>

      <div className="w-full">
        <SearchInput
          className="bg-transparent/5 font-normal placeholder-transparent/40"
          placeholder="Search items, collections, and accounts..."
        />
      </div>
      <MainDropdown isScrolled={isScrolled} />
      <nav>
        <ul className="flex gap-5 items-center">
          <button
            className="hidden  max-[1040px]:flex"
            onClick={visibleHandler}
          >
            {visible ? (
              <FontAwesomeIcon
                className={`${!isScrolled && 'text-white'}`}
                icon={faXmark}
              />
            ) : (
              <FontAwesomeIcon
                className={`${!isScrolled && 'text-white'}`}
                icon={faBars}
              />
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

export default MainHeader;
