import { HeroContainer, HeroTitle } from "./styles";

function Home() {
  return (
    <div>
      {/* Hero */}
      <HeroContainer>
        {/* Hero text */}
        <HeroTitle>
          <span>Connect with nearby bands and artists</span>
          <p>Join bands... or create your own!</p>
        </HeroTitle>
      </HeroContainer>
    </div>
  );
}

export default Home;