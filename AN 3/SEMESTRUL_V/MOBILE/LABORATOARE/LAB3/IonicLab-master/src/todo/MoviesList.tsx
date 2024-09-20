import React, { useContext, useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router';
import MovieComponent from './MovieComponent';
import { getLogger } from '../core';
import { MoviesContext } from './MovieProvider';
import { IonContent, 
         IonHeader, 
         IonList, 
         IonLoading, 
         IonPage, 
         IonTitle, 
         IonToolbar,
         IonToast, 
         IonFab,
         IonFabButton,
         IonIcon,
         IonButton, 
         IonButtons,
         IonInfiniteScroll,
         IonInfiniteScrollContent,
         IonSearchbar,
         IonSelect, IonSelectOption, createAnimation } from '@ionic/react';

import { add } from 'ionicons/icons';
import { AuthContext } from '../auth';
import { NetworkState } from '../pages/NetworkState';
import { Movie } from './Movie';
import styles from "./styles.module.css";

const log = getLogger('MoviesList');
const moviesPerPage = 5;
const filterValues = ["IsPartOfASerie", "IsNotPartOfASerie"];

export const MoviesList: React.FC<RouteComponentProps> = ({ history }) => {
  const { movies, fetching, fetchingError, successMessage, closeShowSuccess } = useContext(MoviesContext);
  const { logout } = useContext(AuthContext);
  const [isOpen, setIsOpen]= useState(false);
  const [index, setIndex] = useState<number>(0);
  const [moviesAux, setMoviesAux] = useState<Movie[] | undefined>([]);
  const [more, setHasMore] = useState(true);
  const [searchText, setSearchText] = useState('');
  const [filter, setFilter] = useState<string | undefined>(undefined);
  //const [hasFetched, setHasFetched] = useState(false);

  //animations
  useEffect(simpleAnimation, []);

  useEffect(()=>{
    if(fetching) setIsOpen(true);
    else setIsOpen(false);
  }, [fetching]);

  log('render');
  console.log(movies);

  function handleLogout(){
    logout?.();
    history.push('/login');
  }

  //pagination
  useEffect(()=>{
    fetchData();
  }, [movies]);

  // searching
  useEffect(()=>{
    if (searchText === "") {
      setMoviesAux(movies);
    }
    if (movies && searchText !== "") {
      setMoviesAux(movies.filter(movie => movie.title!.startsWith(searchText)));
    }
  }, [searchText]);

   // filtering
   useEffect(() => {
    if (movies && filter) {
        setMoviesAux(movies.filter(movie => {
            if (filter === "IsPartOfASerie")
                return movie.seriesPart === true;
            else
                return movie.seriesPart === false;
        }));
    }
}, [filter]);

  function fetchData() {
    if(movies){
      const newIndex = Math.min(index + moviesPerPage, movies.length);
      if( newIndex >= movies.length){
          setHasMore(false);
      }
      else{
          setHasMore(true);
      }
      setMoviesAux(movies.slice(0, newIndex));
      setIndex(newIndex);
    }
  }

  async function searchNext($event: CustomEvent<void>){
    await fetchData();
    await ($event.target as HTMLIonInfiniteScrollElement).complete();
  }

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>MOVIE APP</IonTitle>
          <IonSelect 
            className={styles.selectBar} 
            slot="end" 
            value={filter} 
            placeholder="Filter" 
            onIonChange={(e) => setFilter(e.detail.value)}>
                        {filterValues.map((each) => (
                            <IonSelectOption key={each} value={each}>
                                {each}
                            </IonSelectOption>
                        ))}
          </IonSelect>
          <NetworkState />
          <IonSearchbar className={styles.customSearchBar} placeholder="Search by title" value={searchText} debounce={200} onIonInput={(e) => {
                        setSearchText(e.detail.value!);
                    }} slot="secondary">
          </IonSearchbar>
          <IonButtons slot='end'>
             <IonButton onClick={handleLogout}>Logout</IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonLoading isOpen={isOpen} message="Fetching movies..." />
        {moviesAux && (
          <IonList>
            {moviesAux.map(movie => 
              <MovieComponent key={movie._id} _id={movie._id} 
              producer={movie.producer}
              title={movie.title} 
              duration={movie.duration} 
              dateOfRelease={movie.dateOfRelease}
              seriesPart={movie.seriesPart} 
              isNotSaved={movie.isNotSaved}
              webViewPath={movie.webViewPath}
              onEdit={id => history.push(`/movie/${id}`)} /> 
            )}
          </IonList>
        )}
        <IonInfiniteScroll threshold="100px" disabled={!more} onIonInfinite={(e:CustomEvent<void>) => searchNext(e)} >
          <IonInfiniteScrollContent loadingText="Loading more food..." >
          </IonInfiniteScrollContent>
        </IonInfiniteScroll>
        {fetchingError && (
          <div>{fetchingError.message || 'Failed to fetch movies'}</div>
        )}
        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton className="square-a" onClick={() => history.push('/movie')}>
            <IonIcon icon={add} />
          </IonFabButton>
        </IonFab>
        <IonToast
          isOpen={!!successMessage}
          message={successMessage}
          position='bottom'
          buttons={[
            {
              text: 'Dismiss',
              role: 'cancel',
              handler: () => {
                console.log('More Info clicked');
              },
            }]}
          onDidDismiss={closeShowSuccess}
          duration={5000}
          />
      </IonContent>
    </IonPage>
  );

    function simpleAnimation() {
    const el = document.querySelector('.square-a');
    if (el) {
        const animation = createAnimation()
            .addElement(el)
            .duration(2000)
            .direction('alternate')
            .iterations(Infinity)
            .keyframes([
                { offset: 0, transform: 'scale(1.1)', opacity: '0.5', color: 'white'},
                { offset: 0.5, transform: 'scale(1.3)', opacity: '1', color: 'white'},
                { offset: 1, transform: 'scale(1)', opacity: '0.5', color: 'white'}
            ]);
        animation.play();
    }
  }
};

