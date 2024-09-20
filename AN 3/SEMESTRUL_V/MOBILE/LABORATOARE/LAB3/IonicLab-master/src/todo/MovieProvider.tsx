import React, { useCallback, useEffect, useReducer, useContext } from 'react';
import PropTypes from 'prop-types';
import { getLogger } from '../core';
import { getAllMovies, updateMovieAPI, createMovieAPI, newWebSocket, deleteMovieAPI } from './MovieApi';
import { Movie } from './Movie';
import { AuthContext } from '../auth';
import { useNetwork } from '../pages/useNetwork';
import {useIonToast} from "@ionic/react";
import { Preferences } from '@capacitor/preferences';

const log = getLogger('MovieProvider');

type UpdateMovieFn = (movie: Movie) => Promise<any>;

interface MoviesState {
    movies?: Movie[];
    fetching: boolean;
    fetchingError?: Error | null;
    updating: boolean,
    updateError?: Error | null,
    updateMovie?: UpdateMovieFn,
    addMovie?: UpdateMovieFn,
    successMessage?: string;
    closeShowSuccess?: () => void;
}

interface ActionProps {
    type: string,
    payload?: any,
}

const initialState: MoviesState = {
    fetching: false,
    updating: false,
};

const FETCH_MOVIES_STARTED = 'FETCH_MOVIES_STARTED';
const FETCH_MOVIES_SUCCEEDED = 'FETCH_MOVIES_SUCCEEDED';
const FETCH_MOVIES_FAILED = 'FETCH_MOVIES_FAILED';
const UPDATE_MOVIE_STARTED = 'UPDATE_MOVIE_STARTED';
const UPDATE_MOVIE_SUCCEDED = 'UPDATE_MOVIE_SUCCEDED';
const UPDATE_MOVIE_FAILED = 'UPDATE_MOVIE_FAILED';
const SHOW_SUCCESS_MESSSAGE = 'SHOW_SUCCESS_MESSAGE';
const HIDE_SUCCESS_MESSSAGE = 'HIDE_SUCCESS_MESSAGE';
const CREATE_MOVIE_STARTED = 'CREATE_MOVIE_STARTED';
const CREATE_MOVIE_SUCCEDED = 'CREATE_MOVIE_SUCCEDED';
const CREATE_MOVIE_FAILED = 'CREATE_MOVIE_FAILED';

const reducer: (state: MoviesState, action: ActionProps) => MoviesState 
    = (state, { type, payload }) => {
    switch(type){
        case FETCH_MOVIES_STARTED:
            return { ...state, fetching: true, fetchingError: null };
        case FETCH_MOVIES_SUCCEEDED:
            return {...state, movies: payload.movies, fetching: false };
        case FETCH_MOVIES_FAILED:
            return { ...state, fetchingError: payload.error, fetching: false };
        case UPDATE_MOVIE_STARTED:
            return { ...state, updateError: null, updating: true };
        case UPDATE_MOVIE_FAILED:
            return { ...state, updateError: payload.error, updating: false };
        case UPDATE_MOVIE_SUCCEDED:
            const movies = [...(state.movies || [])];
            const movie = payload.movie;
            const index = movies.findIndex(it => it._id === movie._id);
            movies[index] = movie;
            return { ...state,  movies, updating: false };
        case CREATE_MOVIE_FAILED:
            console.log(payload.error);
          return { ...state, updateError: payload.error, updating: false };
        case CREATE_MOVIE_STARTED:
          return { ...state, updateError: null, updating: true };
        case CREATE_MOVIE_SUCCEDED:
            const beforeMovies = [...(state.movies || [])];
            const createdMovie = payload.movie;
            console.log(createdMovie);
            const indexOfAdded = beforeMovies.findIndex(it => it._id === createdMovie._id || it.title === createdMovie.title);
            console.log("index: ", indexOfAdded);
            if (indexOfAdded === -1) {
                beforeMovies.splice(0, 0, createdMovie);
            } else {
                beforeMovies[indexOfAdded] = createdMovie;
            }
            console.log(beforeMovies);
            console.log(payload);
            return { ...state,  movies: beforeMovies, updating: false, updateError: null };
        case SHOW_SUCCESS_MESSSAGE:
            const allMovies = [...(state.movies || [])];
            const updatedMovie = payload.updatedMovie;
            const indexOfMovie = allMovies.findIndex(it => it._id === updatedMovie._id);
            allMovies[indexOfMovie] = updatedMovie;
            console.log(payload);
            return {...state, movies: allMovies, successMessage: payload.successMessage }
        case HIDE_SUCCESS_MESSSAGE:
            return {...state, successMessage: payload }
        
        default:
            return state;
    }
};

export const MoviesContext = React.createContext(initialState);

interface MovieProviderProps {
    children: PropTypes.ReactNodeLike,
}

export const MovieProvider: React.FC<MovieProviderProps> = ({ children }) => {
    const [state, dispatch] = useReducer(reducer, initialState);
    const { movies, fetching, fetchingError, updating, updateError, successMessage } = state;
    const { token } = useContext(AuthContext);
    const { networkStatus } = useNetwork();
    const [toast] = useIonToast();

    useEffect(getItemsEffect, [token]);
    useEffect(wsEffect, [token]);
    useEffect(executePendingOperations, [networkStatus.connected, token, toast]);

    const updateMovie = useCallback<UpdateMovieFn>(updateMovieCallback, [token]);
    const addMovie = useCallback<UpdateMovieFn>(addMovieCallback, [token]);

    log('returns');

    function getItemsEffect() {
        let canceled = false;
        fetchItems();
        return () => {
            canceled = true;
        }

        async function fetchItems() {
          if(!token?.trim()){
            return;
          }

            try{
                log('fetchMovies started');
                dispatch({ type: FETCH_MOVIES_STARTED });
                const movies = await getAllMovies(token);
                log('fetchItems succeeded');
                if (!canceled) {
                dispatch({ type: FETCH_MOVIES_SUCCEEDED, payload: { movies } });
                }
            } catch (error) {
                log('fetchItems failed');
                if (!canceled) {
                    dispatch({ type: FETCH_MOVIES_FAILED, payload: { error } });
                }
            }
        }
    }

    async function updateMovieCallback(movie: Movie) {
        try {
          log('updateMovie started');
          dispatch({ type: UPDATE_MOVIE_STARTED });
          const updatedMovie = await updateMovieAPI(token, movie);
          log('saveMovie succeeded');
          dispatch({ type: UPDATE_MOVIE_SUCCEDED, payload: { movie: updatedMovie } });
        } catch (error: any) {
          log('updateMovie failed');
          // save item to storage
          console.log('Updating movie locally...');

          movie.isNotSaved = true;
          await Preferences.set({
            key: `upd-${movie.title}`,
            value: JSON.stringify({token, movie })
          });
          dispatch({ type: UPDATE_MOVIE_SUCCEDED, payload: { movie: movie } });
          toast("You are offline... Updating movie locally!", 3000);
    
          if(error.toJSON().message === 'Network Error')
            dispatch({ type: UPDATE_MOVIE_FAILED, payload: { error: new Error(error.response) } });
        }
    }

    async function addMovieCallback(movie: Movie){
        try{
          log('addMovie started');
          dispatch({ type: CREATE_MOVIE_STARTED });
          console.log(token);
          const addedMovie = await createMovieAPI(token, movie);
          console.log(addedMovie);
          log('saveMovie succeeded');
          dispatch({ type: CREATE_MOVIE_SUCCEDED, payload: { movie: addedMovie } });
        }catch(error: any){
          log('addMovie failed');
          console.log(error.response);
          // save item to storage
          console.log('Saving movie locally...');
          const { keys } = await Preferences.keys();
          const matchingKeys = keys.filter(key => key.startsWith('sav-'));
          const numberOfItems = matchingKeys.length + 1;
          console.log(numberOfItems);

          movie._id = numberOfItems.toString(); // ii adaug si id...
          movie.isNotSaved = true;
          await Preferences.set({
            key: `sav-${movie.title}`,
            value: JSON.stringify({token, movie })
          });
          dispatch({ type: CREATE_MOVIE_SUCCEDED, payload: { movie: movie } });
          toast("You are offline... Saving movie locally!", 3000);
    
          if(error.toJSON().message === 'Network Error')
            dispatch({ type: CREATE_MOVIE_FAILED, payload: { error: new Error(error.response || 'Network error') } });
        }
    }

    function executePendingOperations(){
      async function helperMethod(){
          if(networkStatus.connected && token?.trim()){
              log('executing pending operations')
              const { keys } = await Preferences.keys();
              for(const key of keys) {
                  if(key.startsWith("sav-")){
                      const res = await Preferences.get({key: key});
                      console.log("Result", res);
                      if (typeof res.value === "string") {
                          const value = JSON.parse(res.value);
                          value.movie._id=undefined;  // ca sa imi puna serverul id nou!!
                          log('creating item from pending', value);
                          await addMovieCallback(value.movie);
                          await Preferences.remove({key: key});
                      }
                  }
              }
              for(const key of keys) {
                if(key.startsWith("upd-")){
                    const res = await Preferences.get({key: key});
                    console.log("Result", res);
                    if (typeof res.value === "string") {
                        const value = JSON.parse(res.value);
                        log('updating item from pending', value);
                        await updateMovieCallback(value.movie);
                        await Preferences.remove({key: key});
                    }
                }
            }
          }
      }
      helperMethod();
  }

    function wsEffect() {
        let canceled = false;
        log('wsEffect - connecting');
        let closeWebSocket: () => void;
        if(token?.trim()){
          closeWebSocket = newWebSocket(token, message => {
            if (canceled) {
              return;
            }
            const { event, payload } = message;
            console.log('Provider message: ', message);

            log(`ws message, item ${event}`);
            if (event === 'updated') {
              console.log(payload);
              dispatch({ type: SHOW_SUCCESS_MESSSAGE, payload: {successMessage: payload.successMessage, updatedMovie: payload.updatedMovie } });
            }
            else if(event == 'created'){
              console.log(payload);
              dispatch({ type: CREATE_MOVIE_SUCCEDED, payload: { movie: payload.updatedMovie } });
            }
          });
        }
        return () => {
          log('wsEffect - disconnecting');
          canceled = true;
          closeWebSocket?.();
        }
    }

    function closeShowSuccess(){
        dispatch({ type: HIDE_SUCCESS_MESSSAGE, payload: null });
    }

    const value = { movies, fetching, fetchingError, updating, updateError, updateMovie, addMovie, successMessage, closeShowSuccess };

    return (
        <MoviesContext.Provider value={value}>
            {children}
        </MoviesContext.Provider>
    );
};

