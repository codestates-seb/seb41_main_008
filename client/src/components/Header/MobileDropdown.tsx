import styled from 'styled-components';
import { Dispatch, SetStateAction } from 'react';
const MobileDropdownContainer = styled.div<any>`
  display: flex;
  flex-direction: column;
  position: fixed;
  padding: 20px;
  width: 100vw;
  height: 100%;
  right: 0;
  top: 70px;
  background-color: white;
  visibility: ${(props) => (props.visible ? 'visible' : 'hidden')};
  @media screen and (min-width: 1040px) {
    visibility: hidden;
  }
`;

interface MobileDropdownProps {
  visible: boolean;
  children: React.ReactNode;
  setVisible: Dispatch<SetStateAction<boolean>>;
}

const MobileDropdown = ({
  visible,
  children,
  setVisible,
}: MobileDropdownProps) => {
  return (
    <MobileDropdownContainer
      visible={visible}
      onClick={() => setVisible(false)}
    >
      {visible && children}
    </MobileDropdownContainer>
  );
};
export default MobileDropdown;
