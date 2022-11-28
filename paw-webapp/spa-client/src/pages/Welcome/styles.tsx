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

export const Slogan1 = styled.span`
font-weight: 700;
    font-size: 2.5rem;
    line-height: 2.75rem;
    `;

export const Slogan2 = styled.p`
    font-size: 1.5rem;
    line-height: 1.75rem;
    margin-top: 1rem;
`;