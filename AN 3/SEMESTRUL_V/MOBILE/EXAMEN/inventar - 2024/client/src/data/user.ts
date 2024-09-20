import axios from "axios";

export interface User {
  id: string;
}

export const login = async (id: string) => {
  const user: User = { id };
  console.log("user", user);
  const response = await axios.post(`http://localhost:3000/auth`, user);
  return response.data;
};

//pt cache token
export const cacheToken = async (token: string) => {
  localStorage.setItem("token", JSON.stringify(token));
};
export const getToken = (): string => {
  const token = localStorage.getItem("token");
  if (token === null) {
    return "";
  }
  return JSON.parse(token);
};
