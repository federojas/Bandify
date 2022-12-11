
import { AuditionsContainer, RelativeContainer, ParalaxContainer, ParalaxImage, Parallax, AbsoluteContainer, SearchContainer, TitleContainer, Title, AuditionsBlackTitle, PostsContainer  } from "./styles";
import PostCard from "../../components/PostCard/PostCard";
import AuditionsParalax from "../../images/parallax3.png"
import { useTranslation } from "react-i18next";
import AuditionSearchBar from "../../components/AuditionSearchBar";


export default function AuditionsPage() {
  const { t } = useTranslation();
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
              auditionTitle="Buscamos baterista"
              bandName="Los totora"
              location="Buenos Aires, Argentina"
              roles={["Baterista"]}
              genres={["Rock"]}
            />

          </PostsContainer>        
          
        </AuditionsContainer>

      </RelativeContainer>
     
    </>
  );
}
