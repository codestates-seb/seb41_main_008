import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { GrStatusGoodSmall } from 'react-icons/gr';
import styled from 'styled-components';
import BuyAndCartButton from './CartButton/BuyAndCartButton';
import { BiTrash } from 'react-icons/bi';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import customAxios from 'utils/api/axios';
import { useDispatch } from 'react-redux';
import { setRemoveColOpen } from 'store/toastSlice';

type cartBtnType = {
  hide: boolean;
};

interface Item {
  itemDescription: string;
  itemImageName: string;
  itemName: string;
  itemPrice: number;
  ownerName: string;
  itemId: number;
  onSale: boolean;
  ownerId: number;
  coinImage?: string;
}

interface CardType {
  itemName: string;
  itemId: number;
  data: Item[];
  itemImageName: string;
  itemPrice: number;
  onSale: boolean;
  collectionName: string;
  ownerId: number;
  itemDescription: string;
  filter: string;
  coinName: string;
  logoImgName: string;
  collectionId?: number;
}

const HideWrapper = styled.div<cartBtnType>`
  div {
    border-radius: 0px 0px 15px 15px;
    transition: 0.2s;
    visibility: ${(props) => (props.hide ? 'visible' : 'hidden')};
    opacity: ${(props) => (props.hide ? '1' : '0')};
    transform: ${(props) =>
      props.hide ? 'translateY(0px)' : 'translateY(10px)'};
  }
`;
const Card = ({
  data,
  collectionId,
  itemId,
  collectionName,
  itemImageName,
  itemPrice,
  onSale,
  filter,
  coinName,
  itemName,
  logoImgName,
  ownerId,
}: CardType) => {
  const [hide, setHide] = useState<boolean>(false);
  const [show, setShow] = useState<boolean>(false);
  const myId = window.localStorage.getItem('MEMBER_ID');
  const { memberId } = useParams();

  const queryClient = useQueryClient();
  const dispatch = useDispatch();

  const { mutate, error } = useMutation({
    mutationFn: () => customAxios.delete(`api/collections/${collectionId}`),
    onSuccess: () => {
      queryClient.invalidateQueries(['members', memberId]);
      dispatch(setRemoveColOpen(true));
    },
  });

  if (error instanceof Error)
    return <p>An error has occured + {error.message}</p>;

  return (
    <>
      <div className="shadow-lg hover:shadow-2xl rounded-xl font-semibold dark:bg-[#363840] dark:text-white h-full">
        <article
          onMouseEnter={() => {
            setHide(true);
            setShow(true);
          }}
          onMouseLeave={() => {
            setHide(false);
            setShow(false);
          }}
        >
          <Link
            to={
              filter === 'Created'
                ? `/collection/${collectionId}`
                : `/item/${itemId}`
            }
            className="flex flex-col w-full h-full"
          >
            <div className="overflow-hidden relative rounded-t-xl w-full aspect-square ">
              {filter === 'Created' && (
                <button
                  onClick={(e) => {
                    e.preventDefault();
                    mutate();
                  }}
                  className={`rounded-full p-1.5 bg-black/60 hover:bg-black z-10 absolute top-2 right-2 ${
                    show && memberId === myId ? 'inline-block' : 'hidden'
                  } `}
                >
                  <BiTrash className="h-6 w-6 text-gray-300" />
                </button>
              )}
              <img
                className="rounded-t-xl object-cover hover:scale-125 duration-500 h-full w-full"
                src={
                  filter === 'Collected'
                    ? process.env.REACT_APP_IMAGE + itemImageName
                    : process.env.REACT_APP_IMAGE + logoImgName
                }
                alt="NFT"
              />
            </div>
            <div className="flex flex-col p-4 rounded-b-xl gap-2">
              <div>{itemName}</div>
              <div>{collectionName}</div>
              <div className="flex">
                {onSale && <span className="mr-2">{itemPrice}</span>}

                <span>{filter === 'Collected' && coinName}</span>
              </div>
              {onSale ? (
                <div
                  className={`${'flex justify-center items-center w-24 rounded-full bg-green-300 dark:bg-emerald-700 text-green-700 font-bold dark:text-white'}`}
                >
                  <GrStatusGoodSmall className="text-emerald-700 animate-ping w-2 h-2  dark:text-emerald-500" />
                  <span className="m-1">OnSale</span>
                </div>
              ) : (
                <div className="h-[50px]"></div>
              )}
            </div>
          </Link>

          {/**장바구니 버튼 컬렉션엔 랜더링x */}
          {filter !== 'Created' && (
            <HideWrapper hide={hide}>
              <BuyAndCartButton
                ownerId={ownerId}
                data={
                  data?.filter((el) => {
                    return el.itemId === itemId;
                  })[0]
                }
              />
            </HideWrapper>
          )}
        </article>
      </div>
    </>
  );
};
export default Card;
