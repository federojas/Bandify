import styled from "styled-components";

export const PostCardMainContainer= styled.div`
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
`
export const PostCardProfile =styled.div`
display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
  height: 50px;
  margin-bottom: 10px;
`
export const PostCardBandName=styled.h1`
margin-left: 10px;
max-width: 80%;
color: black;
font-size: 1rem;
font-weight: 700;
word-break: break-word;
`
export const PostCardAuditionTitle=styled.h2`
color: #111827;
  font-size: 1.5rem;
  line-height: 1.75rem;
  font-weight: 600;
  word-break: break-word;
  white-space: normal;
`