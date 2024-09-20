import axios from "axios";

export interface Product {
  code: number;
  name: string;
}

export interface ProductResponse {
  total: number;
  page: number;
  products: Product[];
}

export const getProducts = async (page: number): Promise<ProductResponse> => {
  const response = await axios.get(
    `http://localhost:3000/product?page=${page}`
  );
  return response.data;
};

export const cacheProducts = async (item: Product[]) => {
  localStorage.setItem("products", JSON.stringify(item));
};
export const addToCachedProducts = async (item: Product) => {
  const items = getAllCachedProducts();
  items.push(item);
  localStorage.setItem("products", JSON.stringify(items));
};
export const getAllCachedProducts = (): Product[] => {
  const items = localStorage.getItem("products");
  if (items === null) {
    return [];
  }
  return JSON.parse(items);
};
