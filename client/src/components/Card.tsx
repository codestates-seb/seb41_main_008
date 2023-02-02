import { useState } from 'react';
import { Link } from 'react-router-dom';
import { GrStatusGoodSmall } from 'react-icons/gr';
import { TbCircleX } from 'react-icons/tb';
import styled from 'styled-components';
import BuyAndCartButton from './CartButton/BuyAndCartButton';
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
  return (
    <>
      <div className="shadow-lg hover:shadow-2xl rounded-xl font-semibold dark:bg-[#363840] dark:text-white h-full">
        <article
          onMouseEnter={() => {
            setHide(true);
          }}
          onMouseLeave={() => {
            setHide(false);
          }}
        >
          <Link
            to={
              filter === 'Created'
                ? `/collection/${collectionId}`
                : `/item/${itemId}`
            }
            className="flex flex-col "
          >
            <div className="overflow-hidden rounded-t-xl w-full aspect-square ">
              <img
                className="rounded-t-xl object-cover hover:scale-125 duration-500 h-full w-full"
                src={
                  filter === 'Collected'
                    ? process.env.REACT_APP_IMAGE + itemImageName
                    : process.env.REACT_APP_IMAGE + logoImgName
                }
                alt="NFTImage"
              />
            </div>
            <div className="flex flex-col p-4 rounded-b-xl gap-2">
              <div>{itemName}</div>
              <div>{collectionName}</div>
              <div className="flex">
                {onSale && <span className="mr-2">{itemPrice}</span>}

                <span>
                  {filter === 'Collected' && coinName}
                  {/* <img alt="coinImage" src={coinImage} /> */}
                </span>
              </div>
              {onSale ? (
                <div
                  className={` ${'flex  justify-center items-center w-24 rounded-full bg-green-300 dark:bg-emerald-700 text-green-700 font-bold dark:text-white'}`}
                >
                  <GrStatusGoodSmall className="text-emerald-700 animate-ping w-2 h-2  dark:text-emerald-500" />
                  <span className="m-1">OnSale</span>
                </div>
              ) : (
                // <div className="flex justify-center p-1 items-center font-bold  bg-red-300 text-red-700 rounded-full w-28">
                //   <TbCircleX className="w-4 h-4 mr-1" />
                //   Not listed
                // </div>
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
