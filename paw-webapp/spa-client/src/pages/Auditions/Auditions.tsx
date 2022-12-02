
import { AuditionsContainer, RelativeContainer, ParalaxContainer, ParalaxImage, Parallax, AbsoluteContainer, SearchContainer, TitleContainer  } from "./styles";
import PostCard from "../../components/PostCard/PostCard";
import AuditionsParalax from "../../images/parallax3.png"


export default function AuditionsPage() {
  return (
    <>
      <RelativeContainer>
        <ParalaxContainer>
          <Parallax>
             <ParalaxImage src={AuditionsParalax} />
          </Parallax>
        </ParalaxContainer>

        <AbsoluteContainer>
          <SearchContainer>
            <TitleContainer>
              <h1>Discover auditions</h1>
            </TitleContainer>

          </SearchContainer>
          
        </AbsoluteContainer>
        <AuditionsContainer>

        
        
          
        </AuditionsContainer>

      </RelativeContainer>
     
    </>
  );
}
