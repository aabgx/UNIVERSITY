import axios from "axios";

export interface Menu {
  code: number;
  name: string;
}
export interface Query {
  name: string;
}

export const getMenuItems = async (name: string) => {
  const menu: Query = { name };
  const response = await axios.get("http://localhost:3000/MenuItem", {
    params: {
      q: name, // înlocuiește cu interogarea de căutare reală
    },
  });
  return response.data;
};

export const cacheMenu = async (orders: Menu[]) => {
  let alreadyCached = getCachedMenu();
  alreadyCached = [...alreadyCached, ...orders];
  localStorage.setItem(`menu`, JSON.stringify(alreadyCached));
};
export const getCachedMenu = (): Menu[] => {
  const orders = localStorage.getItem(`menu`);
  if (orders === null) {
    return [];
  }
  return JSON.parse(orders);
};
