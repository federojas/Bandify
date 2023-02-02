import styled from "styled-components";

export const PaginationWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 27px;
  font-weight: 700;
  margin: 30px 0;
`;

export const PaginationArrow = styled.img<{ xRotated?: boolean }>`
  transform: ${(props) => (props.xRotated ? "rotate(180deg);" : "")};
  cursor: pointer;
  margin: 0 20px;
  height: 32px;
`;
