import axios from "axios";

export interface Order {
  code: number;
  quantity: number;
  free: boolean;
}

export const saveOrder = async (
  code: number,
  quantity: number,
  free: boolean
) => {
  const order: Order = { code, quantity, free };
  const response = await axios.post("http://localhost:3000/OrderItem", order);
  return response.data;
};

//cache order
export const cacheOrders = async (orders: Order[]) => {
  localStorage.setItem(`order`, JSON.stringify(orders));
};
export const getCachedOrders = (): Order[] => {
  const orders = localStorage.getItem(`order`);
  if (orders === null) {
    return [];
  }
  return JSON.parse(orders);
};

//cache order de salvat
export const cacheOrdersDeSalvat = async (orders: Order[]) => {
  localStorage.setItem(`order_nonsaved`, JSON.stringify(orders));
};
export const getCacheOrdersDeSalvat = (): Order[] => {
  const orders = localStorage.getItem(`order_nonsaved`);
  if (orders === null) {
    return [];
  }
  return JSON.parse(orders);
};
