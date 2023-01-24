import { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import BuyAndCartButton from './CartButton/BuyAndCartButton';
type cartBtnType = {
  hide: boolean;
};

interface Item {
  // itemDescription: string;
  //   itemImageName: string;
  //   itemName: string;
  //   itemPrice: number;
  //   ownerName: string;
  itemId: number;
  onSale: boolean;
  ownerId: number;
}

interface CardType {
  ownerId: number;
  itemId: number;
  data: Item[];
  collectionName: string;
  logoImgName: string;
  itemImageName: string;
  itemPrice: number;
  itemDescription: string;
  filter: string;
  coinName: string;
  onSale: boolean;
}

const HideWrapper = styled.div<cartBtnType>`
  div {
    transition: 0.2s;
    visibility: ${(props) => (props.hide ? 'visible' : 'hidden')};
    opacity: ${(props) => (props.hide ? '1' : '0')};
    transform: ${(props) =>
      props.hide ? 'translateY(0px)' : 'translateY(20px)'};
  }
`;
const Card = ({
  ownerId,
  data,
  itemId,
  collectionName,
  logoImgName,
  itemImageName,
  itemPrice,
  itemDescription,
  filter,
  coinName,
  onSale,
}: CardType) => {
  const [hide, setHide] = useState<boolean>(false);
  return (
    <div className="shadow hover:shadow-2xl rounded-b-xl">
      <article
        onMouseEnter={() => {
          setHide(true);
        }}
        onMouseLeave={() => {
          setHide(false);
        }}
      >
        <Link to={'/'} className="flex flex-col hover:shadow">
          <div className="overflow-hidden rounded-t-xl w-full aspect-square">
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
          <div className="flex flex-col p-4 rounded-b-xl">
            <div>{itemDescription}</div>
            <div>{collectionName}</div>
            <div className="flex">
              <span className="mr-2">{itemPrice}</span>
              <span>{filter === 'Collected' && coinName}</span>
            </div>
          </div>
        </Link>
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
