import styled from 'styled-components';
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
  div {
    top: 25px;
    visibility: hidden;
    transition: 0.8s all;
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
  return (
    <header className="flex justify-center items-center border-2 p-4 sticky top-0 bg-[#dbedfa]">
      <div className="flex gap-2 mr-2">
        <div>logo</div>
        <div>domainName</div>
      </div>
      <div className="w-full">
        <SearchInput placeholder="Search items, collections, and accoutns..." />
      </div>
      <nav className="ml-2 cursor-pointer">
        <ul className="flex gap-5 mr-8 ml-2 max-[1040px]:hidden">
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
      <nav className="ml-2 cursor-pointer">
        <ul className="flex gap-5">
          <li className="max-[640px]:hidden">Signup</li>
          <li className="max-[640px]:hidden">Login</li>
          <li>cart</li>
          <button>dropdown</button>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
