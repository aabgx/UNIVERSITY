import axios from "axios";
import { getCachedToken } from "./user";

export interface Menu {
  code: number;
  name: string;
}
export interface Query {
  name: string;
}

export const getMenuItems = async (name: string) => {
  let token = getCachedToken();
  const menu: Query = { name };
  const response = await axios.get("http://localhost:3000/MenuItem", {
    params: {
      q: name, // înlocuiește cu interogarea de căutare reală
    },
    headers: {
      Authorization: token, // înlocuiește cu tokenul tău de autorizare real
    },
  });
  return response.data;
};
