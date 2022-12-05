import syled from 'styled-components';

export const AuditionsContainer = syled.div`
    display: flex;
    flex-direction: column;
    background-color: #f3f3f6;
    align-items: center;
    padding: 2rem 1rem;

    h2 {
        font-size: 3rem;
        line-height: 1;
        font-weight: 700;
        padding: 1rem;
        margin: 1rem;
    }

`;

export const RelativeContainer = syled.div`
    position: relative;
`;

export const AbsoluteContainer = syled.div`
    position: absolute;
    top: 2%;
    left: 2%;
`;

export const ParalaxContainer = syled.div`
    position: relative;
    overflow: hidden;
    height: 500px;
`;

export const ParalaxImage = syled.img`

`;
// display: none;
//     position: absolute;
//     left: 50%;
//     bottom: 0;
//     min-width: 100%;
//     min-height: 100%;
//     -webkit-transform: translate3d(0, 0, 0);
//     transform: translate3d(0, 0, 0);
//     transform: translateX(-50%);
export const Parallax = syled.div`
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: -1;

    img{
        display: block;
        position: absolute;
        left: 50%;
        bottom: 0;
        minwidth: 100%;
        minheight: 100%;
        -webkit-transform: translate3d(0, 0, 0);
        transform: translate3d(0, 0, 0);
        transform: translateX(-50%);
    }
`;

export const SearchContainer = syled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    
`;

export const TitleContainer = syled.div`
    marginLeft: 2rem;
    marginTop: 2rem;
    flex-direction: column;
    justify-content: space-between;

    h1{
        color: ${(props) => props.theme.white};
    }
`;

export const Title = syled.h1`
    color: ${(props) => props.theme.white};
`;

export const AuditionsBlackTitle = syled.h2`
    color: ${(props) => props.theme.black};
    font-size: xxx-large;
    font-weight: bold;
`;

export const PostsContainer = syled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    margin: 0.75rem;
    justify-content: space-around;
`;

