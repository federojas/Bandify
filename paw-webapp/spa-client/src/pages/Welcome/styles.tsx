import styled from "styled-components";
import guitarHero from '../../images/guitar.png';

export const HeroContainer = styled.div`
    position: relative;
    width: 100%;
    background-image: url(${guitarHero});
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    height: 100vh;
    display: flex;
`;

export const HeroTitle = styled.div`
    position: absolute;
    top: 10%;
    left: 5%;
    color: #efefef;
    width: 50%;    
`