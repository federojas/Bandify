import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { ChakraProvider, ColorModeScript, extendTheme, type ThemeConfig } from "@chakra-ui/react";
import '@fontsource/poppins'
import './common/i18n/index'

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const config: ThemeConfig = {
  initialColorMode: 'dark',
  useSystemColorMode: false,
}

const fonts = {
  heading: `'Poppins', sans-serif`,
  body: `'Poppins', sans-serif`,
}

const theme = extendTheme({ config, fonts });

root.render(
  <React.StrictMode>
    <ChakraProvider theme={theme}>
      <ColorModeScript initialColorMode={theme.config.initialColorMode} />
      <App />
    </ChakraProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
