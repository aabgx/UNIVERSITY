import React, { useCallback, useContext, useEffect, useState } from 'react';
import {
  IonButton,
  IonButtons,
  IonContent,
  IonHeader,
  IonInput,
  IonLoading,
  IonPage,
  IonTitle,
  IonToolbar,
  IonBackButton,
  IonLabel
} from '@ionic/react';
import { getLogger } from '../core';
import { RouteComponentProps } from 'react-router';
import { MoviesContext } from './MovieProvider';
import { Movie } from './Movie';
import styles from './styles.module.css';

const log = getLogger('EditLogger');

interface MovieEditProps extends RouteComponentProps<{
  id?: string;
}> {}

export const MovieEdit: React.FC<MovieEditProps> = ({ history, match }) => {
  const { movies, updating, updateError, updateMovie} = useContext(MoviesContext);
  const [title, setTitle] = useState('');
  const [duration, setDuration] = useState('');
  const [movieToUpdate, setMovieToUpdate] = useState<Movie>();

  useEffect(() => {
    const routeId = match.params.id || '';
    console.log(routeId);
    const movie = movies?.find(it => it._id === routeId);
    setMovieToUpdate(movie);
    if(movie){
      setTitle(movie.title);
      setDuration(movie.duration.toString());
    }
  }, [match.params.id, movies]);

  const handleUpdate = useCallback(() => {
    const editedMovie ={ ...movieToUpdate, title: title, duration: parseFloat(duration) };
    log(editedMovie);
    console.log(updateMovie);
    updateMovie && updateMovie(editedMovie).then(() => editedMovie.duration && history.goBack());
  }, [movieToUpdate, updateMovie, title, duration, history]);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
        <IonButtons slot="start">
            <IonBackButton></IonBackButton>
          </IonButtons>
          <IonTitle>EDIT</IonTitle>
          <IonButtons slot="end">
            <IonButton onClick={handleUpdate}>
              UPDATE
            </IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonInput label="Title:" className={styles.customInput} placeholder="New Title" value={title} onIonInput={e => setTitle(prev => e.detail.value || '')} />
        <IonInput label="Duration:" className={styles.customInput} placeholder="New duration" value={duration} onIonInput={e => e.detail.value ? setDuration(prev => e.detail.value!) : setDuration('') }/>
        <IonLoading isOpen={updating} />
        {updateError && (
          <div className={styles.errorMessage}>{updateError.message || 'Failed to update item'}</div>
        )}
      </IonContent>
    </IonPage>
  );
}
