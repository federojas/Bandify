import styled from "styled-components";
import guitarDrums from "../../images/guitar-drums.jpg";

export const DiscoverBg = styled.div`
    background-image: url(${guitarDrums});
    position: relative;
    width: 100%;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    height: 100vh;
    opacity: 0.9;
    display: flex;
`;