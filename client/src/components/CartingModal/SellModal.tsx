import styled from 'styled-components';
import { ModalBack } from './CartingModal';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { useEffect, useRef, useState } from 'react';
import { closeSell } from 'store/modalSlice';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { getItemsData, getCoinPrice, sellItemHandler } from 'utils/api/api';
import { SubmitHandler, useForm } from 'react-hook-form';

import { ItemProps } from 'components/ItemDetail/ItemDetail';

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

interface FormValue {
  itemPrice: number;
}
const SellModal = () => {
  const dispatch = useAppDispatch();
  const ref = useRef<HTMLDivElement>(null);
  const [data, setData] = useState<ItemProps>();
  const [isCheckd, setIsChecked] = useState(false);
  const [coinPrice, setCoinPrice] = useState(0);
  const { sellOpen } = useAppSelector((state) => state.modal);
  const { itemId } = useAppSelector((state) => state.modal);
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
    resetField,
  } = useForm<FormValue>({ mode: 'onChange' });
  useEffect(() => {
    getItemsData(itemId).then((res) => setData(res.data));
  }, [itemId]);

  /**모달 오픈시 모달창영역밖 클릭했을떄 모달닫는 기능*/
  const modalClose = (e: MouseEvent) => {
    if (sellOpen && ref.current?.contains(e.target as Node)) {
      dispatch(closeSell());
      resetField('itemPrice');
      setIsChecked(false);
    }
  };
  useEffect(() => {
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  });

  useEffect(() => {
    getCoinPrice(data?.coinName)
      .then((res) => setCoinPrice(res[0].trade_price))
      .catch((err) => console.log(err));
  }, [data?.coinName]);
  // console.log('totalPrice', totalPrice);
  const onClickSubmit: SubmitHandler<FormValue> = (data) => {
    console.log(errors);
    if (totalPrice > 999999999999999) {
      alert('999999999999999₩ 을 초과할 수 없습니다');
      return;
    }
    if (window.confirm('판매하시겠습니까?')) {
      sellItemHandler(itemId, data).then(() => {
        dispatch(closeSell());
        window.location.replace(`/items/${itemId}`);
      });
    }
  };
  const onError = (err: {}) => {
    console.log(err);
  };
  const totalPrice = coinPrice * watch('itemPrice');
  return (
    <>
      {sellOpen && <ModalBack ref={ref} zIndex={'50'} />}
      {sellOpen && (
        <SellModalContainer>
          <form onSubmit={handleSubmit(onClickSubmit, onError)}>
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
                <div className="flex items-center rounded-xl h-full border-2 p-2">
                  <input
                    type="number"
                    className={`${'w-full'} ${
                      watch('itemPrice') < 0
                        ? 'bg-red-200 border-red-600 border-2  focus:outline-none'
                        : null
                    }`}
                    required
                    {...register('itemPrice', {
                      min: 0,
                      valueAsNumber: true,
                    })}
                  />
                  <div className="border-l-2">{data?.coinName}</div>
                </div>
                {totalPrice > 999999999999999 ? (
                  <div className="text-red-700 font-light">
                    Maximum is 999,999,999,999,999₩
                  </div>
                ) : (
                  <div className="truncate flex  flex-col">
                    <div className="flex justify-between">
                      <div>Service fee</div>
                      10%
                    </div>
                    <div className="flex justify-between">
                      <div>Listing price:</div>
                      {isNaN(totalPrice) ? '--' : totalPrice.toLocaleString()}₩
                    </div>
                    <div className="flex justify-between">
                      <div>Potential earning:</div>
                      {isNaN(totalPrice)
                        ? '--'
                        : (totalPrice - totalPrice * 0.1).toLocaleString()}
                      ₩
                    </div>
                  </div>
                )}
                {watch('itemPrice') < 0 && <span>s</span>}
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
                >
                  판매등록버튼
                </button>
              </div>
            </footer>
          </form>
        </SellModalContainer>
      )}
    </>
  );
};
export default SellModal;
