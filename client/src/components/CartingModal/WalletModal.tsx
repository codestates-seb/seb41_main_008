import styled from 'styled-components';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { ModalBack } from './CartingModal';
import { closeWallet } from 'store/modalSlice';
const WalletContainer = styled.div`
  display: flex;
  position: fixed;
  background-color: white;
  width: 450px;
  height: 100vh;
  z-index: 20;
  top: 90px;
  right: 0;
  /* border: 1px solid black; */
`;

const WalletModal = () => {
  const dispatch = useAppDispatch();
  const { walletOpen } = useAppSelector((state) => state.modal);
  return (
    <>
      {walletOpen && <ModalBack zIndex={'20'} />}
      {walletOpen && (
        <WalletContainer>
          <header>
            <div>닉네임</div>
            <div>멤버아이디</div>
          </header>
          <button onClick={() => dispatch(closeWallet())}>x</button>
        </WalletContainer>
      )}
    </>
  );
};
export default WalletModal;
