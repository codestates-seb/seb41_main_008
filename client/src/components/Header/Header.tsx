import styled from 'styled-components';
import { useState, useEffect, FormEvent } from 'react';
import {
  Link,
  createSearchParams,
  useNavigate,
  useLocation,
} from 'react-router-dom';
import { useAppSelector } from '../../hooks/hooks';
import { faBars, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import MobileDropdownList from './MobileDropdownLIst';
import MobileDropdown from './MobileDropdown';
import Dropdown from './Dropdown';
import logo from '../../../src/assets/logo.png';

const SearchInput = styled.input`
  display: flex;
  width: 90%;
  padding: 8px;
  border-radius: 10px;
  outline: none;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
`;

const Header = () => {
  const [isScrolled, setIsScrolled] = useState(false);
  const [visible, setVisible] = useState(false);
  const [searchValue, setSearchValue] = useState<string>('');

  const navigate = useNavigate();
  const location = useLocation();

  const params = { q: searchValue };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (!searchValue.trim()) return;

    navigate({
      pathname: '/search',
      search: `?${createSearchParams(params)}`,
    });
  };

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

  const walletState = useAppSelector((state) => state.modal.walletOpen);

  const home = location.pathname === '/';

  return (
    <header
      className={`duration-300 transition-colors flex justify-center items-center font-bold p-4 fixed top-0 left-0 right-0 z-20 text-lg bg-white text-black dark:bg-[#202225] dark:text-white
       ${!isScrolled && home && !walletState && 'bg-[#FED7C8]'}`}
    >
      <div className="flex items-center gap-2 mr-2">
        <Link to={'/'} className="w-12 h-12">
          <img
            src={logo}
            alt="Logo"
            className="w-full h-full rounded-full object-cover bg-[#202225]"
          />
        </Link>
        <Link to={'/'}>NFTeam</Link>
      </div>

      <div className="w-full">
        <form onSubmit={handleSubmit}>
          <SearchInput
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            className="bg-transparent/5 font-normal placeholder-transparent/40 dark:bg-white dark:text-black"
            placeholder="Search items, collections, and accounts..."
          />
        </form>

        <button type="submit" className="hidden" />
      </div>
      <Dropdown isScrolled={isScrolled} home={home} />
      <nav>
        <ul className="flex gap-5 items-center ">
          <button
            className="hidden   max-[1040px]:flex"
            onClick={visibleHandler}
          >
            {visible ? (
              <FontAwesomeIcon
                className={`${walletState && 'text-black'} ${
                  !isScrolled && home && 'text-white'
                }`}
                icon={faXmark}
              />
            ) : (
              <FontAwesomeIcon
                className={`${walletState && 'text-black'} ${
                  !isScrolled && home && 'text-white'
                }`}
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
