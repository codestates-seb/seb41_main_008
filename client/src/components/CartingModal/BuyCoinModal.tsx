import styled from 'styled-components';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { ModalBack } from './CartingModal';
import { closeBuyCoin, closeWallet } from 'store/modalSlice';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useState, useEffect } from 'react';
import { getCoinPrice, buyCoin, kakaoPay } from 'utils/api/api';
import { useLocation, useNavigate } from 'react-router-dom';
const BuyCoinContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 450px;
  width: 550px;
  background-color: white;
  margin: 0px auto;
  top: 50%;
  left: 0;
  right: 0;
  transform: translateY(-50%);
  z-index: 30;
  font-weight: bold;
  border-radius: 20px;
  padding: 20px;
  box-shadow: 4px 8px 8px hsl(0deg 0% 0% / 0.38);
`;
interface FormValue {
  coinCount: number;
  coinName: string;
}
const coinOptions = [
  { id: 1, name: 'SOL' },
  { id: 2, name: 'BTC' },
  { id: 3, name: 'DOGE' },
  { id: 4, name: 'ETH' },
  { id: 5, name: 'ETC' },
];
const BuyCoinModal = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const { buyCoinOpen } = useAppSelector((state) => state.modal);
  const [coinName, setCoinName] = useState('SOL');
  const [tradePrice, setTradePrice] = useState(0);
  const memberId = localStorage.getItem('MEMBER_ID');
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<FormValue>({ mode: 'onChange' });

  const selectBoxHandler = (e: any) => {
    setCoinName(e.target.value);
  };

  const pgToken = location.search.split('=')[1];
  const tid = localStorage.getItem('tid');
  const paidPrice = tradePrice * watch('coinCount');

  const onClickSubmit: SubmitHandler<FormValue> = (data: any) => {
    const coinFee = tradePrice * watch('coinCount') * 0.01;
    const totalPrice = coinFee + tradePrice * watch('coinCount');
    console.log('totalPrice', totalPrice);
    console.log(errors);
    buyCoin({ ...data, totalPrice }).then((res: any) => {
      window.location.href = res.data.next_redirect_pc_url;
      localStorage.setItem('tid', res.data.tid);
      dispatch(closeWallet());
    });
  };
  useEffect(() => {
    console.log('a');
    kakaoPay(pgToken, tid).then(() => {
      dispatch(closeBuyCoin());
      navigate(`/account/${memberId}`);
    });
  }, [pgToken, tid, dispatch, memberId, navigate]);

  useEffect(() => {
    getCoinPrice(coinName).then((res) => setTradePrice(res[0].trade_price));
  }, [coinName]);

  return (
    <>
      {buyCoinOpen && <ModalBack zIndex={'30'} />}
      {buyCoinOpen && (
        <BuyCoinContainer>
          <header className="flex justify-between items-center p-2 w-full border-b-2">
            <div className="grow text-center text-2xl">
              Buy crypto with KAKAO PAY
            </div>
            <button className="grow-0" onClick={() => dispatch(closeBuyCoin())}>
              x
            </button>
          </header>
          <form
            className="p-4 bg-[#1c1c1e] rounded-xl"
            onSubmit={handleSubmit(onClickSubmit)}
          >
            <section className="">
              <div>
                <div>
                  <div className="flex">
                    <h1 className="flex-1 text-xl  text-white">Buy Crypto</h1>
                  </div>
                  <div className="flex flex-col">
                    <label
                      htmlFor="selectCoin"
                      className="text-xs text-[rgba(235,235,245,0.6)] mt-8 mb-1"
                    >
                      I want to buy
                    </label>
                    <input
                      id="selectCoin"
                      type="text"
                      className="bg-[#3d3d41] text-white p-2 rounded-xl "
                      {...register('coinCount', {
                        valueAsNumber: true,
                        pattern: {
                          value: /^(0|[1-9]\d*)(\.\d+)?$/,
                          message: '숫자만 입력가능합니다.',
                        },
                      })}
                    />
                    <select
                      className="rounded-xl"
                      {...register('coinName')}
                      onChange={selectBoxHandler}
                    >
                      {coinOptions.map((el) => {
                        return (
                          <option
                            key={el.id}
                            defaultValue={coinOptions[0].name}
                          >
                            {el.name}
                          </option>
                        );
                      })}
                    </select>
                    <div className="flex flex-col mt-8">
                      <div className="text-[rgba(235,235,245,0.6)] text-xs mb-1">
                        I have to spend
                      </div>
                      <div className="bg-[#3d3d41] text-white p-2 rounded-xl">
                        {isNaN(paidPrice) ? '--' : paidPrice.toLocaleString()}₩
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </section>
            <footer className="">
              <div className="flex items-center justify-center p-2 bg-emerald-600 text-white rounded-xl mt-8 ">
                <button>카카오페이 호출버튼</button>
              </div>
            </footer>
          </form>
        </BuyCoinContainer>
      )}
    </>
  );
};
export default BuyCoinModal;
