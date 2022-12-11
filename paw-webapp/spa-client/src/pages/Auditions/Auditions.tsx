
import { AuditionsContainer, RelativeContainer, ParalaxContainer, ParalaxImage, Parallax, AbsoluteContainer, SearchContainer, TitleContainer, Title, AuditionsBlackTitle, PostsContainer  } from "./styles";
import PostCard from "../../components/PostCard/PostCard";
import AuditionsParalax from "../../images/parallax3.png"
import { useTranslation } from "react-i18next";
import AuditionSearchBar from "../../components/AuditionSearchBar";
import { Audition } from "../../types";


export default function AuditionsPage() {
  const { t } = useTranslation();
  const audition: Audition = {
    band: {
      name: "My Band",
      id: 1,
    },
    id: 1,
    creationDate: new Date(),
    title: "My Band is looking for a drummer",
    roles: ["Drummer"],
    genres: ["Rock"],
    location: "Buenos Aires",
  };

  return (
    <>
      <head>
        <title>{t("Auditions.title")}</title>
      </head>
      <RelativeContainer>
        <ParalaxContainer>
          <Parallax>
             <ParalaxImage src={AuditionsParalax} />
          </Parallax>
        </ParalaxContainer>

        <AbsoluteContainer>
          <SearchContainer>
            <TitleContainer>
              <Title>{t("Auditions.discover")}</Title>
              {/* TODO: NO ANDA EL THEMING DE STYLED COMPONENTS */}
              <AuditionSearchBar/>
            </TitleContainer>

          </SearchContainer>
          
        </AbsoluteContainer>
        <AuditionsContainer>
          <AuditionsBlackTitle>{t("Auditions.latest")}</AuditionsBlackTitle>
          <PostsContainer>
            <PostCard
              {...audition}
            />

          </PostsContainer>        
          
        </AuditionsContainer>

      </RelativeContainer>
     
    </>
  );
}
