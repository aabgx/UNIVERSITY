import { Redirect, Route } from 'react-router-dom';
import { IonApp, IonRouterOutlet, setupIonicReact } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

import { MoviesList } from './todo/MoviesList';
import { MovieProvider } from './todo/MovieProvider';
import { MovieEdit } from './todo/MovieEdit';
import { MovieAdd } from './todo/MovieAdd';
import { AuthProvider, Login, PrivateRoute } from './auth';

setupIonicReact();

const App: React.FC = () => (
  <IonApp>
      <IonReactRouter>
        <IonRouterOutlet>
          <AuthProvider>
            <Route path="/login" component={Login} exact={true}/>
            <MovieProvider>
              <PrivateRoute path="/movies" component={MoviesList} exact={true} />
              <PrivateRoute path="/movie" component={MovieAdd} exact={true}/>
              <PrivateRoute path="/movie/:id" component={MovieEdit} exact={true}/>
            </MovieProvider>
            <Route exact path="/" render={() => <Redirect to="/movies"/>}/>
          </AuthProvider>
        </IonRouterOutlet>
      </IonReactRouter>
  </IonApp>
);

export default App;
