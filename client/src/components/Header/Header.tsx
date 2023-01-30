import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAppSelector } from 'hooks/hooks';
import { faBars, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import MobileDropdownList from './MobileDropdownLIst';
import MobileDropdown from './MobileDropdown';
import Dropdown from './Dropdown';

const SearchInput = styled.input`
  display: flex;
  width: 100%;
  padding: 8px;
  border-radius: 10px;
  outline: none;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
`;

const Header = () => {
  const location = useLocation();
  const { walletOpen } = useAppSelector((state) => state.modal);
  const [home, setHome] = useState<boolean | undefined>();
  const [isScrolled, setIsScrolled] = useState(false);
  const [visible, setVisible] = useState(false);

  const visibleHandler = () => {
    setVisible(!visible);
  };

  useEffect(() => {
    if (location.pathname === '/') {
      setHome(true);
    } else setHome(false);

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
  }, [location.pathname]);

  return (
    <header
      className={`duration-300 flex justify-center items-center font-bold z-20 p-4 fixed w-full text-lg  ${
        (!home || walletOpen) && 'bg-white'
      } ${isScrolled && home && 'bg-white'}`}
    >
      <div className="flex gap-2 mr-2">
        <Link to={'/'}>logo</Link>
        <Link className={`${!isScrolled && home && 'text-white'}`} to={'/'}>
          NFTeam
        </Link>
      </div>

      <div className="w-full">
        <SearchInput
          className="bg-transparent/5 font-normal placeholder-transparent/40"
          placeholder="Search items, collections, and accounts..."
        />
      </div>
      <Dropdown isScrolled={isScrolled} home={home} />
      <nav>
        <ul className="flex gap-5 items-center">
          <button
            className="hidden  max-[1040px]:flex"
            onClick={visibleHandler}
          >
            {visible ? (
              <FontAwesomeIcon
                className={`${!isScrolled && home && 'text-white'}`}
                icon={faXmark}
              />
            ) : (
              <FontAwesomeIcon
                className={`${!isScrolled && home && 'text-white'}`}
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

export default Header;
