import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { BandifyLogoImg } from '../../components/NavBar/styles';
import BandifyLogo from '../../images/logo.png';

import '../../styles/forms.css';
import '../../styles/register.css';
import '../../js/alerts.js';

import RegisterBandForm from '../../components/RegisterBandForm';

const RegisterContent: React.FC<{ isBand: boolean }> = ({ isBand }) => {
  const toggleForm = (isBand: boolean) => {
    // code to toggle the form goes here
  };

  return (
    <main>
      <div className="register-content flex flex-col">
        <div className="card-content">
          <div className="header">
            Register.header
            <div className="forms-buttons">
              {isBand ? (
                <>
                  <button
                    id="artist-button"
                    onClick={() => toggleForm(false)}
                    style={{ backgroundColor: 'rgba(108, 12, 132, 0.69)' }}
                  >
                    Register.artist_word
                  </button>
                  <button id="band-button" onClick={() => toggleForm(true)}>
                    Register.band_word
                  </button>
                </>
              ) : (
                <>
                  <button id="artist-button" onClick={() => toggleForm(false)}>
                    Register.artist_word
                  </button>
                  <button
                    id="band-button"
                    onClick={() => toggleForm(true)}
                    style={{ backgroundColor: 'rgba(108, 12, 132, 0.69)' }}
                  >
                    Register.band_word
                  </button>
                </>
              )}
            </div>
          </div>
          {!isBand ? (
            <>
              <div id="artist-form" style={{ display: 'block' }}>
                {/* TODO Include the registerArtistForm component here */}
                {/* <RegisterArtistForm artist={1} /> */}
              </div>
              <div id="band-form" style={{ display: 'none' }}>
                {/* TODO Include the registerBandForm component here */}
                <RegisterBandForm />
              </div>
            </>
          ) : (
            <>
              <div id="artist-form" style={{ display: 'none' }}>
                {/* TODO Include the registerArtistForm component here */}
                {/* <RegisterArtistForm artist={1} /> */}
              </div>
              <div id="band-form" style={{ display: 'block' }}>
                {/* TODO  Include the registerBandForm component here */}
                <RegisterBandForm/>
              </div>
            </>
          )}
        </div>
      </div>
      <div id="snackbar">Snackbar.message</div>
    </main>
  );
};

export default RegisterContent;
