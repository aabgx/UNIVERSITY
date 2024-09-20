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
  IonLabel,
  IonDatetime,
  IonSelect,
  IonSelectOption
} from '@ionic/react';
import { getLogger } from '../core';
import { RouteComponentProps } from 'react-router';
import { MoviesContext } from './MovieProvider';
import { Movie } from './Movie';
import styles from './styles.module.css';

const log = getLogger('SaveLogger');

interface MovieEditProps extends RouteComponentProps<{
  id?: string;
}> {}

export const MovieAdd: React.FC<MovieEditProps> = ({ history, match }) => {
  const { movies, updating, updateError, addMovie } = useContext(MoviesContext);
  const [title, setTitle] = useState('');
  const [duration, setDuration] = useState('');
  const [producer, setProducer] = useState('');
  const [date, setDate] = useState(new Date());
  const [option, setOption] = useState(true);
  const [movieToUpdate, setMovieToUpdate] = useState<Movie>();

  const handleAdd = useCallback(() => {
    const editedMovie ={ ...movieToUpdate, title: title, producer: producer, duration: parseFloat(duration), dateOfRelease: date, seriesPart: option };
    log(editedMovie);
    console.log(updateError);
    addMovie && addMovie(editedMovie).then(() => editedMovie.duration && history.goBack());
  }, [movieToUpdate, addMovie, title, duration, date, producer, option, history]);

  const dateChanged = (value: any) => {
    let formattedDate = value;
    console.log(formattedDate);
    setDate(formattedDate);
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
        <IonButtons slot="start">
            <IonBackButton></IonBackButton>
          </IonButtons>
          <IonTitle>EDIT</IonTitle>
          <IonButtons slot="end">
            <IonButton onClick={handleAdd}>
              ADD
            </IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonInput label="Title:" className={styles.customInput} placeholder="New Title" value={title} onIonInput={e => setTitle(prev => e.detail.value || '')} />
        <IonInput label="producer:" className={styles.customInput} placeholder="New producer" value={producer} onIonInput={e => setProducer(prev => e.detail.value || '')} />
        <IonInput label="Duration:" className={styles.customInput} placeholder="New duration" value={duration} onIonInput={e => e.detail.value ? setDuration(prev => e.detail.value!) : setDuration('') }/>
        <IonInput label="DateOfRelease:" className={styles.customInput} placeholder="Choose date" value={new Date(date).toDateString()} />
        <IonDatetime
                onIonChange={(e) => dateChanged(e.detail.value)}>
        </IonDatetime>
        <IonInput label="Other series movies:" className={styles.customInput} placeholder="True/False" value={option==true ? 'True' : 'False'} />
        <IonSelect value={option} onIonChange={e => setOption(e.detail.value)}>
          <IonSelectOption value={true}>
            {'True'}
          </IonSelectOption>
          <IonSelectOption value={false}>
            {'False'}
          </IonSelectOption>
        </IonSelect>
        <IonLoading isOpen={updating} />
        {updateError && (
          <div className={styles.errorMessage}>{updateError.message || 'Failed to save item'}</div>
        )}
      </IonContent>
    </IonPage>
  );
}
