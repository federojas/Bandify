import React from "react";
import '../../styles/aboutUs.css';

const messages = {
    whatIsBandiy: 'What is Bandiy?',
    bandifyTeam: 'The Bandify team',
    bandifyAbout: 'About Bandify',
    inBandify: 'In Bandify',
    beArtist: 'Be an Artist',
    asArtist: 'As an artist, you can...',
    artistLookingFor: 'If you are an artist looking for...',
    completeArtistForm: 'Complete the artist form',
    waitForContactArtist: 'Wait for us to contact you',
    beBand: 'Be a Band',
    asBand: 'As a band, you can...',
    bandLookingFor: 'If you are a band looking for...',
    completeBandForm: 'Complete the band form',
    mailingBand: 'Send us an email',
    goToRegister: 'Go to register',
  };

const AboutUs = () => {
  return (
    <div className="about-section">
      <div>
        <h1 className="about-us">{messages.whatIsBandiy}</h1>
        <span>{messages.bandifyTeam}</span>
        <br />
        <span>{messages.bandifyAbout}</span>
      </div>
      <br />
      <hr />
      <h2 className="about-us-h2-center-text card-be-header about-us">
        {messages.inBandify}
      </h2>
      <div className="items">
        <div className="card">
          <h1 className="card-be-header">{messages.beArtist}</h1>
          <hr />
          <p>{messages.asArtist}</p>
          <p>{messages.artistLookingFor}</p>

          <ul>
            <li>{messages.completeArtistForm}</li>
            <li>{messages.waitForContactArtist}</li>
          </ul>
          <br />
        </div>
        <div className="card">
          <h1 className="card-be-header">{messages.beBand}</h1>
          <hr />
          <p>{messages.asBand}</p>
          <p>{messages.bandLookingFor}</p>

          <ul>
            <li>{messages.completeBandForm}</li>
            <li>{messages.mailingBand}</li>
          </ul>
          <br />
        </div>
      </div>
      <br />
      <div className="buttons">
        <a href={"/register"} className="purple-hover-button">
          {messages.goToRegister}
        </a>
      </div>
    </div>
  );
};

export default AboutUs;
