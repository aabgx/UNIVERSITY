import axios from "axios";

export interface User {
  table: number;
}

export const auth = async (table: number) => {
  const user: User = { table };
  const token = await axios.post("http://localhost:3000/auth", user);
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
