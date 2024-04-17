import styled from 'styled-components';
import CONSTANTS from '../../constants';

export const Button = styled.button`
  clip-path: circle();
  width: 40px;
  height: 40px;
  border: none;
  background-color:${props => {
      if(props.color){
        return props.color
      } else {
        return '#fff'
      }
    }
  };
  cursor: pointer;
  transition: background-color 0.3s;
`;

export const CarouselHeading = styled.h2`
  font-family: "MontserratBold";
  font-weight: 700;
  font-size: 30px;
  text-transform: uppercase;
  line-height: normal;

  @media screen and (max-width: 1279px){
    font-size: 26px
  }
  @media screen and (max-width: 767px){
    font-size: 22px
  }
`

export const ButtonBorder = styled.div`
  width: 44px;
  height: 44px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  background-color: ${CONSTANTS.COLORS.SAPPHIRE_900};
  clip-path: circle();
  transition-duration: 0.4ms;
  
  &:hover {
    background-color: ${CONSTANTS.COLORS.STEEL_900}
  }
`;

export const ButtonWrapper = styled.div`
  width: 44px;
  height: 44px;
  display: inline-block;
`;
