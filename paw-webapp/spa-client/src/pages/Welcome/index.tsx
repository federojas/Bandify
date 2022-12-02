import { HeroContainer, HeroTitle, Slogan1, Slogan2 } from "./styles";

function Home() {
  return (
    <div>
      {/* Hero */}
      <HeroContainer>
        {/* Hero text */}
        <HeroTitle>
          <Slogan1>Connect with nearby bands and artists</Slogan1>
          <Slogan2>Join bands... or create your own!</Slogan2>
        </HeroTitle>
      </HeroContainer>
    </div>
  );
}

export default Home;