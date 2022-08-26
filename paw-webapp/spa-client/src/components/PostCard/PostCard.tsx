import {
  Heading,
  Avatar,
  Box,
  Center,
  Text,
  Stack,
  Button,
  Link,
  Badge,
  useColorModeValue,
} from "@chakra-ui/react";
import {
  PostCardMainContainer,
  PostCardProfile,
  PostCardBandName,
  PostCardAuditionTitle,
  PostCardRolesSpan,
  PostCardGenresSpan,
  PostCardButtonContainer,
  PostCardButton,
} from "./styles";
export default function PostCard() {
  return (
    <>
      <PostCardMainContainer>
        <PostCardProfile>
          {/* TODO: ALT MESSAGE */}
          {/* TODO: a href */}

          <img
            style={{
              width: "50px",
              height: "50px",
              objectFit: "cover",
              borderRadius: "1000px",
              border: "2px solid black",
            }}
            src="https://cdn-1.motorsport.com/images/amp/6O1P1km2/s1000/jos-verstappen-1.jpg"
          ></img>
          <PostCardBandName>Los totora</PostCardBandName>
        </PostCardProfile>

        {/* --------------- postcard title -------------  */}
        <div style={{ marginLeft: "20px" }}>
          <div style={{ marginBottom: "1rem", overflow: "hidden !important" }}>
            {/* TODO: BAND TITLE HARDCODED */}
            <PostCardAuditionTitle>
              Se busca baterista para la banda
            </PostCardAuditionTitle>
          </div>
          {/* --------------------------------------------  */}

          {/* ---------- Location roles and genres ------- */}
          <ul>
            {/* LOCATION */}
            <li
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "left",
                  alignItems: "center",
                }}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  width="14"
                  height="25"
                >
                  <path d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z" />
                </svg>
                <div style={{ marginLeft: "0.5rem" }}> Buenos Aires</div>
              </div>
            </li>
            {/* ROLES */}
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                flexWrap: "wrap",
                marginTop: "2px",
                marginBottom: "2px",
              }}
            >
              <div style={{ marginTop: "15px" }}>
                {/* TODO: FOREACH */}
                {/* TODO: curl con los searchlinks */}
                <a href="#">
                  <PostCardRolesSpan>Guitarrista</PostCardRolesSpan>
                  <PostCardRolesSpan>Cantante</PostCardRolesSpan>
                  <PostCardRolesSpan>DJ</PostCardRolesSpan>
                </a>
              </div>
            </div>
            {/* GENRES */}
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                flexWrap: "wrap",
                marginTop: "2rem",
                marginBottom: "2rem",
              }}
            >
              {/* TODO: FOREACH */}
              {/* TODO: curl con los searchlinks */}
              <a href="#">
                <PostCardGenresSpan>Rock</PostCardGenresSpan>
                <PostCardGenresSpan>Pop</PostCardGenresSpan>
                <PostCardGenresSpan>Trap</PostCardGenresSpan>
              </a>
            </div>
          </ul>
          {/* --------------------------------------------  */}
          <PostCardButtonContainer>
            <a href="#">
              <PostCardButton type="button">
                {/* TODO: SPRING MESSAGE */}
                Ver más
              </PostCardButton>
            </a>
          </PostCardButtonContainer>
        </div>
      </PostCardMainContainer>
    </>
  );
}
