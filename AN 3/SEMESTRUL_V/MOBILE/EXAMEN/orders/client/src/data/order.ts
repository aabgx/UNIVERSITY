import axios from "axios";
import { getCachedToken } from "./user";

export interface Order {
  code: number;
  quantity: number;
  table: number;
  free: boolean;
}
export interface OrderSave {
  code: number;
  quantity: number;
  free: boolean;
}

export const saveOrder = async (
  code: number,
  quantity: number,
  free: boolean
) => {
  const token = getCachedToken();
  const order: OrderSave = { code, quantity, free };
  const response = await axios.post("http://localhost:3000/OrderItem", order, {
    headers: {
      Authorization: token,
    },
  });
  return response.data;
};

//cache order
export const cacheOrders = async (orders: Order[]) => {
  localStorage.setItem(`order${orders[0].table}`, JSON.stringify(orders));
};
export const getCachedOrders = (table: number): Order[] => {
  const orders = localStorage.getItem(`order${table}`);
  if (orders === null) {
    return [];
  }
  return JSON.parse(orders);
};
