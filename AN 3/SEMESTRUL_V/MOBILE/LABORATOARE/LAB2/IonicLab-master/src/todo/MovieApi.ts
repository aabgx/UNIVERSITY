import axios from "axios";
import { getLogger, authConfig, baseUrl, withLogs } from "../core";
import { Movie } from "./Movie";
import { Preferences } from "@capacitor/preferences";

const log = getLogger('movieLogger');

const getBooksUrl = `http://${baseUrl}/api/movie`;
const updateBookUrl = `http://${baseUrl}/api/movie`;
const createMovieUrl = `http://${baseUrl}/api/movie`;

export const getAllMovies: (token: string) => Promise<Movie[]> = (token) => {
    return withLogs(axios.get(getBooksUrl, authConfig(token)), 'getAllMovies');
}

export const updateMovieAPI: (token: string, movie: Movie) => Promise<Movie[]> = (token, movie) => {
    return withLogs(axios.put(`${updateBookUrl}/${movie._id}`, movie, authConfig(token)), 'updateMovie');
}

export const createMovieAPI: (token: string, movie: Movie) => Promise<Movie[]> = (token, movie) => {
  return withLogs(axios.post(`${createMovieUrl}`, movie, authConfig(token)), 'createMovie');
}

export const deleteMovieAPI: (token: string, id: string) => Promise<Movie[]> = (token, id) => {
  return withLogs(axios.delete(`${createMovieUrl}/${id}`, authConfig(token)), 'deleteMovie');
}

interface MessageData {
    event: string;
    payload: {
      successMessage: string,
      updatedMovie: Movie
    };
}

export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
    const ws = new WebSocket(`ws://${baseUrl}`)
    ws.onopen = () => {
      log('web socket onopen');
      ws.send(JSON.stringify({type: 'authorization', payload :{token}}));
    };
    ws.onclose = () => {
      log('web socket onclose');
    };
    ws.onerror = error => {
      log('web socket onerror', error);
    };
    ws.onmessage = messageEvent => {
      log('web socket onmessage');
      console.log(messageEvent.data);
      onMessage(JSON.parse(messageEvent.data));
    };
    return () => {
      ws.close();
    }
}

  