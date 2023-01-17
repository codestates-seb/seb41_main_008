import styled from 'styled-components';
import { useState } from 'react';
import BuyAndCartButton from './CartButton/BuyAndCartButton';
type cartBtnType = {
  hide: boolean;
};
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
const Card = () => {
  const [hide, setHide] = useState<boolean>(false);
  return (
    <div className="shadow rounded-b-xl">
      <article
        onMouseEnter={() => {
          setHide(true);
        }}
        onMouseLeave={() => {
          setHide(false);
        }}
      >
        <div className="flex flex-col h-full w-full ">
          <div className="overflow-hidden rounded-t-xl">
            <img
              className="rounded-t-xl object-fit hover:scale-125  duration-500"
              src="https://i.seadn.io/gcs/files/d3781f86c3ff626070559d01a85b1f0f.png?auto=format&w=1000"
              alt=""
            />
          </div>
          <div className="flex flex-col  p-4 rounded-b-xl">
            <div>nftnumber</div>
            <div>nftcollectionname</div>
            <div>nftprice</div>
          </div>
          <HideWrapper hide={hide}>
            <BuyAndCartButton />
          </HideWrapper>
        </div>
      </article>
    </div>
  );
};
export default Card;
