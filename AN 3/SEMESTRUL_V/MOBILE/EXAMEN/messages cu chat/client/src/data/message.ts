import axios from "axios";
import { cacheRooms, getCachedToken } from "./user";

export interface Message {
  id: number;
  text: string;
  created: number;
  room: string;
  username: string;
}

export const cacheLastCreated = async (lastCreated: number) => {
  localStorage.setItem("lastCreated", JSON.stringify(lastCreated));
};
export const getCachedLastCreated = (): number => {
  const lastCreated = localStorage.getItem("lastCreated");
  if (lastCreated === null) {
    return 0;
  }
  return JSON.parse(lastCreated);
};

export const cacheMessages = async (messages: Message[]) => {
  localStorage.setItem("messages", JSON.stringify(messages));
};
export const getCachedMessages = (): Message[] => {
  const messages = localStorage.getItem("messages");
  if (messages === null) {
    return [];
  }
  return JSON.parse(messages);
};

export const getMessages = async (): Promise<Message[]> => {
  let token = getCachedToken();
  console.log(token);
  const lastCreated = getCachedLastCreated();
  const { data } = await axios.get(
    `http://localhost:3000/message?created=${lastCreated}`,
    {
      headers: {
        token: token,
      },
    }
  );

  cacheLastCreated(data[data.length - 1].created);
  cacheMessages(data);

  console.log(data);
  return data;
};

export const getRooms = (messages: Message[]) => {
  const rooms = new Set<string>();
  messages.forEach((message) => {
    rooms.add(message.room);
  });
  return Array.from(rooms);
};
