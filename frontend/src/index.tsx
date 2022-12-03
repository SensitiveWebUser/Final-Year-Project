import React from 'react';
import ReactDOM from 'react-dom';
import { I18nextProvider } from 'react-i18next';

import App from './App';
import i18n from './i18n';
import reportWebVitals from './reportWebVitals';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';

import {
  CssBaseline,
  responsiveFontSizes,
  Slide,
  ThemeProvider,
} from '@mui/material';
import { SnackbarProvider } from 'notistack';

import darkTheme from './styles/darkTheme';
const theme = responsiveFontSizes(darkTheme);

ReactDOM.render(
  <React.StrictMode>
    <SnackbarProvider
      maxSnack={3}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      TransitionComponent={Slide}
      hideIconVariant
      transitionDuration={{ exit: 200, enter: 200 }}
      autoHideDuration={3500}
    >
      <I18nextProvider i18n={i18n}>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <App />
        </ThemeProvider>
      </I18nextProvider>
    </SnackbarProvider>
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorkerRegistration.unregister();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
