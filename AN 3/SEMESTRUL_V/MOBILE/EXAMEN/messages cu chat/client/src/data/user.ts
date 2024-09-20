import axios from "axios";

export interface User {
  username: string;
}

export const auth = async (username: string) => {
  const user: User = { username };
  const token = await axios.post("http://localhost:3000/login", user);
  return token.data.token;
};

export const cacheToken = async (token: string) => {
  localStorage.setItem("token", JSON.stringify(token));
};
export const getCachedToken = (): string => {
  const token = localStorage.getItem("token");
  if (token === null) {
    return "";
  }
  return JSON.parse(token);
};

export const cacheRooms = (rooms: string[]) => {
  localStorage.setItem("rooms", JSON.stringify(rooms));
};
export const getCachedRooms = (): string[] => {
  const rooms = localStorage.getItem("rooms");
  if (rooms === null) {
    return [];
  }
  return JSON.parse(rooms);
};
