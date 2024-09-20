import axios from "axios";

export interface Item {
  code: number;
  name: string;
}

export const getItems = async () => {
  const response = await axios.get("http://localhost:3000/Items");
  return response.data;
};
export const postItem = async (item: Item) => {
  const response = await axios.post("http://localhost:3000/Items", item);
  return response.data;
};

//pt cache
export const cacheItems = async (item: Item[]) => {
  localStorage.setItem("items", JSON.stringify(item));
};
export const addToCachedItems = async (item: Item) => {
  const items = getCachedItems();
  items.push(item);
  localStorage.setItem("items", JSON.stringify(items));
};
export const getCachedItems = (): Item[] => {
  const items = localStorage.getItem("items");
  if (items === null) {
    return [];
  }
  return JSON.parse(items);
};
