import { Menu } from "../data/menu";

export const connect = (callback: any) => {
  const ws = new WebSocket("ws://localhost:3000");
  ws.onopen = () => {
    console.log("connected");
  };
  ws.onmessage = (message) => {
    const msg: Menu[] = JSON.parse(message.data);
    callback(msg);
  };
  ws.onclose = () => {
    console.log("disconnected");
  };
  ws.onerror = () => {
    console.log("error");
  };
  return ws;
};
