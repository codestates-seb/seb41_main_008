import { useState } from 'react';
import { Link } from 'react-router-dom';
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
      props.hide ? 'translateY(0px)' : 'translateY(20px)'};
  }
`;
const Card = ({
  collectionId,
  data,
  itemId,
  collectionName,
  itemImageName,
  itemPrice,
  onSale,
  filter,
  coinName,
  itemName,
  itemDescription,
  logoImgName,
  ownerId,
}: CardType) => {
  const [hide, setHide] = useState<boolean>(false);
  return (
    <div className="shadow-lg hover:shadow-2xl rounded-xl font-semibold">
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
              : `/items/${itemId}`
          }
          className="flex flex-col "
        >
          <div className="overflow-hidden rounded-t-xl w-full aspect-square">
            <img
              className="rounded-t-xl object-cover hover:scale-125 duration-500 h-full w-full"
              src={
                filter === 'Collected'
                  ? process.env.REACT_APP_IMAGE + itemImageName!
                  : process.env.REACT_APP_IMAGE + logoImgName!
              }
              alt="NFTImage"
            />
          </div>
          <div className="flex flex-col p-4 rounded-b-xl">
            <div>{itemName}</div>
            <div>{collectionName}</div>
            <div className="flex">
              {onSale && <span className="mr-2">{itemPrice}</span>}
              <span>
                {filter === 'Collected' && coinName}
                {/* <img alt="coinImage" src={coinImage} /> */}
              </span>
            </div>
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
  );
};
export default Card;
