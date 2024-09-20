import axios from "axios";

export interface Item {
  code: number;
  quantity: number;
}

//pt cache
export const cacheItems = async (item: Item[]) => {
  localStorage.setItem("items-comandate", JSON.stringify(item));
};
export const addToCachedItems = async (item: Item) => {
  const items = getCachedItems();
  items.push(item);
  localStorage.setItem("items-comandate", JSON.stringify(items));
};
export const getCachedItems = (): Item[] => {
  const items = localStorage.getItem("items-comandate");
  if (items === null) {
    return [];
  }
  return JSON.parse(items);
};

//pt cache ceva necomandat inca
export const cacheItemsNecomandate = async (item: Item[]) => {
  localStorage.setItem("items", JSON.stringify(item));
};
export const addToCachedItemsNecomandate = async (item: Item) => {
  const items = getCachedItems();
  items.push(item);
  localStorage.setItem("items", JSON.stringify(items));
};
export const getCachedItemsNecomandate = (): Item[] => {
  const items = localStorage.getItem("items");
  if (items === null) {
    return [];
  }
  return JSON.parse(items);
};

export const postItem = async (item: Item): Promise<void> => {
  const response = await axios.post("http://localhost:3000/item", item);
  return response.data;
};
