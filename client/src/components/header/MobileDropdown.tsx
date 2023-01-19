import styled from 'styled-components';

const MobileDropdownContainer = styled.div<MobileDropdownProps>`
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
}

const MobileDropdown = ({ visible, children }: MobileDropdownProps) => {
  return (
    <MobileDropdownContainer visible={visible}>
      {visible && children}
    </MobileDropdownContainer>
  );
};
export default MobileDropdown;
