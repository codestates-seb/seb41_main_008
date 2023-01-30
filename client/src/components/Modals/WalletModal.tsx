import styled from 'styled-components';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { useEffect, useRef, useState } from 'react';
import { ModalBack } from './CartingModal';
import { closeWallet, openBuyCoin } from 'store/modalSlice';
import { getMyCoin, getCoinPrice } from 'utils/api/api';
import { IoChevronBackSharp } from 'react-icons/io5';
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
  @media screen and (max-width: 1024px) {
    width: 100vw;
    height: 100vh;
    section {
      padding: 40px;
    }
  }
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
  const [totalbalance, setTotalbalance] = useState<number>(0);
  const [eachCoinPrice, setEachCoinPrice] = useState<number[]>([]);
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

  /**업비트오픈 api로 파라미터를 전달하기위해 코인이름만 추출*/
  const coinNames = data.map((el) => el.coinName);

  /**업비트오픈 api를 사용하여 받아온 실시간 현재가격과 코인갯수를 곱해주기위하여 코인갯수 추출 */
  const coinCounts = data.map((el) => el.coinCount);

  useEffect(() => {
    getMyCoin().then((res) => setData(res.data));
  }, [totalbalance]);

  /**업비트오픈api를 사용하여 받아온 각 코인들의 현재가 들을 담아주기 위하여 배열 변수를 선언 */
  const tradePriceArr: number[] = [];
  /**업비트오픈api 를 사용하여 각 코인별 현재가*갯수 를 구한후 그의 총 합을 구하는 함수 */
  const getCoinPriceList = async () => {
    for (const item of coinNames) {
      let result = await getCoinPrice(item);
      tradePriceArr.push(result?.data[0].trade_price);
    }
    const totalbalance = tradePriceArr
      .map((el: number, index: number) => {
        return coinCounts[index] * el;
      })
      .reduce((prev, curr) => prev + curr, 0);
    setTotalbalance(totalbalance);
    setEachCoinPrice(tradePriceArr);
  };

  useEffect(() => {
    getCoinPriceList();
  }, [walletOpen]);

  return (
    <>
      {walletOpen && <ModalBack zIndex={'10'} ref={ref} />}
      {walletOpen && (
        <WalletContainer>
          <header className="flex justify-between items-center w-full p-4 border-b-2">
            <div className="flex">
              <button
                onClick={() => dispatch(closeWallet())}
                className="hidden max-[764px]:block"
              >
                <IoChevronBackSharp />
              </button>
              {data[0]?.nickname}
            </div>
            <div>ID: {memberId}</div>
          </header>
          <section className="p-4">
            <div className="flex flex-col justify-center items-center text-center">
              <div className="flex flex-col justify-center items-center p-4 w-full gap-4 border-x-2 border-t-2 rounded-t-xl">
                <div>Total balance</div>
                <div>{totalbalance.toLocaleString()} ₩</div>
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
                          <div>
                            {(
                              el.coinCount * eachCoinPrice[index]
                            ).toLocaleString()}
                            ₩
                          </div>
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
