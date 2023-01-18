import styled from 'styled-components';
import BuynowButton from './BuynowButton';
import CartButton from './CartButton';

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  color: white;
  width: 100%;
  height: 100%;
`;
const BuyAndCartButton = () => {
  return (
    <ButtonWrapper>
      <CartButton />
      <BuynowButton />
    </ButtonWrapper>
  );
};
export default BuyAndCartButton;
