import styled from 'styled-components';
import DropdownItems from './DropdownItems';
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
const Dropdown = () => {
  return (
    <nav className="ml-2 cursor-pointer">
      <ul className="flex items-center gap-5 mr-8 ml-2 max-[1040px]:hidden">
        <DropdownList>
          <span>Explore</span>
          <DropdownItems />
        </DropdownList>
        <DropdownList>
          <span>Menu1</span>
          <DropdownItems />
        </DropdownList>
        <DropdownList>
          <span>Menu2</span>
          <DropdownItems />
        </DropdownList>
      </ul>
    </nav>
  );
};
export default Dropdown;
