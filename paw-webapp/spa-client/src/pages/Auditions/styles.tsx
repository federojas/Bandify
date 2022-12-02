import syled from 'styled-components';

export const AuditionsContainer = syled.div`
    display: flex;
    flex-direction: column;
    background-color: #F5F5F5;
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
`;


