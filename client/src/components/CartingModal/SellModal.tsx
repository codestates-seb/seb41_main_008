import styled from 'styled-components';
import { ModalBack } from './CartingModal';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { useEffect, useRef, useState, useMemo } from 'react';
import { closeSell } from 'store/modalSlice';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { getItemsData, getCoinPrice } from 'utils/api/api';
const SellModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  position: fixed;
  height: 750px;
  width: 450px;
  border-radius: 15px;
  z-index: 60;
  top: 50%;
  transform: translateY(-50%);
  margin: 0 auto;
  left: 0;
  right: 0;
`;
interface ItemProps {
  coinId: number;
  coinName: string;
  collectionId: string;
  itemDescription: string;
  itemId: number;
  itemImageName: string;
  itemName: string;
  itemPrice: number;
  onSale: boolean;
  trueownerId: number;
  ownerName: string;
  priceHistory: null;
  tradeHistory: null;
  withdrawFee: number;
}
const SellModal = () => {
  const [data, setData] = useState<ItemProps>();
  const [isCheckd, setIsChecked] = useState(false);
  const [sellPrice, setSellPrice] = useState(0);
  const [coinPrice, setCoinPrice] = useState(0);
  const { sellOpen } = useAppSelector((state) => state.modal);
  const { itemId } = useAppSelector((state) => state.modal);
  const dispatch = useAppDispatch();
  const ref = useRef<HTMLDivElement>(null);
  console.log(coinPrice);
  useEffect(() => {
    console.log('a');
    getItemsData(itemId).then((res) => setData(res.data));
  }, [itemId]);
  /**모달 오픈시 모달창영역밖 클릭했을떄 모달닫는 기능*/
  const modalClose = (e: MouseEvent) => {
    if (sellOpen && ref.current?.contains(e.target as Node)) {
      dispatch(closeSell());
      setSellPrice(0);
      setIsChecked(false);
    }
  };
  useEffect(() => {
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  });
  const sellPriceHandler = (e: any) => {
    setSellPrice(e.target.value);
  };
  const submitHandler = () => {
    /**판매등록 로직 작성 */
  };

  useEffect(() => {
    getCoinPrice(data?.coinName)
      .then((res) => setCoinPrice(res[0].trade_price))
      .catch((err) => console.log(err));
  }, [data?.coinName, coinPrice]);
  const totalPrice = (coinPrice * Number(sellPrice)).toLocaleString();
  return (
    <>
      {sellOpen && <ModalBack ref={ref} />}
      {sellOpen && (
        <SellModalContainer>
          <header className="flex justify-between items-center w-full px-4 py-2 border-b-2">
            <h3 className="text-lg font-semibold">List for Sale</h3>
            <FontAwesomeIcon
              className="cursor-pointer"
              icon={faXmark}
              onClick={() => dispatch(closeSell())}
            />
          </header>
          <section>
            <div>
              <img
                className="w-full border-b-2"
                alt="nftimage"
                src={`${process.env.REACT_APP_IMAGE}${data?.itemImageName}`}
              />
            </div>
            <div className="px-3 py-2">{data?.itemName}</div>
            <div className="px-3">
              <div className="flex items-center mt-1 border-2 rounded-xl">
                <div className="grow truncate">
                  {Number(sellPrice).toLocaleString()}
                </div>
                <div className="border-l-2 p-2 font-semibold bg-gray-300 rounded-r-lg">
                  {data?.coinName}
                </div>
              </div>
              {totalPrice.length > 33 ? (
                <div className="text-red-700 font-light">
                  Maximum is 9,999,999,999,999,999,999,999,999₩
                </div>
              ) : (
                <div className="truncate">Total: {totalPrice}₩</div>
              )}
              <input
                type={'text'}
                className="border-2 w-full p-2 rounded-xl"
                onChange={sellPriceHandler}
              />
            </div>
          </section>
          <footer className="p-3">
            <div className="flex justify-center items-center mt-1 mb-1">
              <input
                id="sellApprove"
                type={'checkbox'}
                required
                onChange={(e) => {
                  setIsChecked(e.target.checked);
                }}
              />
              <label htmlFor="sellApprove" className="text-xs ml-1">
                판매등록 후 에는 철회 불가합니다. 동의하십니까?
              </label>
            </div>
            <div>
              <button
                disabled={isCheckd ? false : true}
                className={`${'p-2 rounded-xl w-full bg-emerald-600 text-white'} ${
                  isCheckd ? 'bg-opacity-100' : 'bg-opacity-50'
                }`}
                onClick={submitHandler}
              >
                판매등록버튼
              </button>
            </div>
          </footer>
        </SellModalContainer>
      )}
    </>
  );
};
export default SellModal;
