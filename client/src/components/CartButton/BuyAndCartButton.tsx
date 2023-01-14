import styled from 'styled-components';
import BuynowButton from './BuynowButton';
import CartButton from './CartButton';

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  color: white;
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
