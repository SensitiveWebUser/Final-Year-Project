import { Suspense } from 'react';
import { Provider } from 'react-redux';
import {
  BrowserRouter as Router,
  Outlet,
  Route,
  Routes,
} from 'react-router-dom';
import store from 'redux/store';

import { CircularProgress, Container, Grid, Typography } from '@mui/material';

import Home from 'pages';

const isLoading = false;

const App = (): JSX.Element =>
  isLoading ? (
    <Container>
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justifyContent="center"
        style={{ minHeight: '100vh' }}
      >
        <Typography variant="h4" color="primary.main" mb={4}>
          Loading...
        </Typography>
        <CircularProgress />
      </Grid>
    </Container>
  ) : (
    <Suspense
      fallback={
        <>
          <Container />
        </>
      }
    >
      <Provider store={store}>
        <Router>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="*" element={<Outlet />} />
          </Routes>
        </Router>
      </Provider>
    </Suspense>
  );

export default App;
