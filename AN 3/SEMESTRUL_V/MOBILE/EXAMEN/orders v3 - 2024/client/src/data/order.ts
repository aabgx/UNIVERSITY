import axios from "axios";

export interface Order {
  code: number;
  quantity: number;
}

export const cacheOrder = async (order: Order[]) => {
  localStorage.setItem(`order`, JSON.stringify(order));
};

export const getCachedOrder = (): Order[] => {
  const order = localStorage.getItem(`order`);
  if (order === null) {
    return [];
  }
  return JSON.parse(order);
};

export const postOrder = async (order: Order) => {
  const response = await axios.post(`http://localhost:3000/item`, order);

  console.log("YUHUUUU:", response.data);
  return response.data;
};
