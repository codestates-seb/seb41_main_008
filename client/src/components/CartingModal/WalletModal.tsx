import styled from 'styled-components';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { useEffect, useRef, useState } from 'react';
import { ModalBack } from './CartingModal';
import { closeWallet, openBuyCoin } from 'store/modalSlice';
import { getMyCoin } from 'utils/api/api';
const WalletContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  background-color: white;
  width: 450px;
  height: 100vh;
  z-index: 20;
  top: 76px;
  right: 0;
  font-weight: 600;
  @media screen and (max-width: 764px) {
    width: 100vw;
    height: 100vh;
  }
  /* border: 1px solid black; */
`;
interface WalletInfo {
  memberId: number;
  nickname: string;
  coinId: number;
  coinName: string;
  coinCount: number;
  coinImage: string;
}
const WalletModal = () => {
  const dispatch = useAppDispatch();
  const { walletOpen } = useAppSelector((state) => state.modal);
  const [data, setData] = useState<WalletInfo[]>([]);
  const memberId = localStorage.getItem('MEMBER_ID');
  const ref = useRef<HTMLDivElement>(null);

  const modalClose = (e: MouseEvent) => {
    if (walletOpen && ref.current?.contains(e.target as Node)) {
      dispatch(closeWallet());
    }
  };
  useEffect(() => {
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  });
  const buyCoinHandler = () => {
    dispatch(openBuyCoin());
  };
  useEffect(() => {
    console.log('s');
    getMyCoin().then((res) => setData(res.data));
  }, [walletOpen]);
  console.log(data);

  return (
    <>
      {walletOpen && <ModalBack zIndex={'20'} ref={ref} />}
      {walletOpen && (
        <WalletContainer>
          <header className="flex justify-between items-center w-full p-4 border-b-2">
            {<div>{data[0]?.nickname}</div>}
            <div>ID: {memberId}</div>
          </header>
          <section className="p-4">
            <div className="flex flex-col justify-center items-center text-center">
              <div className="flex flex-col justify-center items-center p-4 w-full gap-4 border-x-2 border-t-2 rounded-t-xl">
                <div>Total balance</div>
                <div>99999₩</div>
              </div>
              <div className="p-4 bg-emerald-600 hover:bg-emerald-500 w-full rounded-b-xl text-white">
                <button onClick={buyCoinHandler}>Add funds</button>
              </div>
            </div>
            {data.length > 0 ? (
              <div className="border-2 rounded-lg mt-8">
                <ul>
                  {data.map((el, index) => {
                    return (
                      <li
                        key={index}
                        className="flex justify-between items-center p-2 w-full border-b-2"
                      >
                        <div className=" w-6 h-6">
                          <img
                            className="grow-0 w-full h-full"
                            src={el.coinImage}
                            alt=""
                          />
                        </div>
                        <div className="grow ml-1">{el.coinName}</div>
                        <div className="flex  flex-col">
                          <div>{el.coinCount}</div>
                          <div>{el.coinCount * 12}₩</div>
                        </div>
                      </li>
                    );
                  })}
                </ul>
              </div>
            ) : null}
          </section>
        </WalletContainer>
      )}
    </>
  );
};
export default WalletModal;
