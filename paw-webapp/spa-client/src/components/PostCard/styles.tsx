import styled from "styled-components";

export const PostCardMainContainer = styled.div`
  padding: 1.25rem;
  margin-left: 0.5rem;
  margin-right: 0.5rem;
  margin-bottom: 1.25rem;
  background-color: #ffffff;
  justify-content: center;
  max-width: 28rem;
  border-radius: 0.5rem;
  border-width: 1px;
  transition: 1s;
  width: 400px;
  height: 440px;
  position: relative;
  :hover{
  transform: translateY(-10px);

  }
`;
export const PostCardProfile = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
  height: 50px;
`;
export const PostCardBandName = styled.h1`
  margin-left: 10px;
  max-width: 80%;
  color: black;
  font-size: 1rem;
  font-weight: 700;
  word-break: break-word;
`;
export const PostCardAuditionTitle = styled.h2`
  color: #111827;
  font-size: 1.5rem !important; 
  line-height: 1.75rem;
  font-weight: 600;
  word-break: break-word;
  white-space: normal;
  magin: 0 !important;
`;
export const PostCardRolesSpan = styled.span`
  padding: 0.25rem 0.5rem;
  background-color: #4aaabc;
  border-radius: 0.25rem;
  color: #fff;
  font-size: 1.25rem;
  line-height: 1.25rem;
  margin: 0.25rem;
`;
export const PostCardGenresSpan = styled.span`
  padding: 0.25rem 0.5rem;
  background-color: #3b7d9d;
  border-radius: 0.25rem;
  color: #fff;
  font-size: 1.25rem;
  line-height: 1.25rem;
  margin: 0.25rem;
`;
export const PostCardButtonContainer = styled.div`
  display: flex;
  flex-direction: row-reverse;
  position: absolute;
  bottom: 7%;
  right: 5%;
`;
export const PostCardButton = styled.button`
  padding: 0.5rem 1.25rem;
  color: #ffffff;
  font-weight: 600;
  line-height: 1.25rem;
  justify-content: flex-end;
  border-radius: 9999px;
  background-color: #6c0c84;
  text-decoration: none;
  border: none;
  :hover {
    background-color: #0369a1;
  }
`;

export const PostCardLocation = styled.li`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const Location = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
`;

export const RolesContainer = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin-top: 2px;
  margin-bottom: 2px;
`;

export const LoopDiv = styled.div`
  margin-top: 15px;
`;

export const GenresContainer = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin-top: 2px;
  margin-bottom: 2px;
  padding-top: 1.25rem;
`;