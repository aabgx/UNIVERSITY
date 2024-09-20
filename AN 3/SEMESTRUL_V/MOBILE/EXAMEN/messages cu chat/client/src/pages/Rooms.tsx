import {
  IonButton,
  IonContent,
  IonHeader,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonPage,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import ExploreContainer from "../components/ExploreContainer";
import "./Home.css";
import { useEffect, useState } from "react";
import { auth, cacheToken, getCachedRooms, getCachedToken } from "../data/user";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import {
  Message,
  getCachedMessages,
  getMessages,
  getRooms,
} from "../data/message";

export interface Rooms {
  sentMessage: Message | undefined;
}

const Rooms: React.FC<Rooms> = ({ sentMessage }) => {
  const [input, setInput] = useState<string>("");
  const navigate = useNavigate();
  const [rooms, setRooms] = useState<string[]>([]);
  const [selectedRoom, setSelectedRoom] = useState<string>("");
  const [messages, setMessages] = useState<Message[]>([]);

  useEffect(() => {
    const rooms = getCachedRooms();
    setRooms(rooms);
  }, []);
  useEffect(() => {
    const messagesNow = getCachedMessages();
    console.log("messagesNow[1].username: ", messagesNow[1].username);
    console.log("selected room: ", selectedRoom);
    const roomGood = "r" + selectedRoom;
    console.log("roomGood: ", roomGood);
    const filteredMessages = messagesNow.filter(
      (message) => message.room === roomGood
    );
    console.log("filteredMessages: ", filteredMessages);
    setMessages(filteredMessages);
  }, [selectedRoom]);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>ROOMS PAGE</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Blank</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonLabel>
          {Object.keys(rooms).map((room) => (
            <IonButton
              color={"secondary"} //culoarea butoanelor de sus
              onClick={() => {
                setSelectedRoom(room);
              }}
            >
              ROOM {room}
            </IonButton>
          ))}
        </IonLabel>
        <IonList>
          {messages.map((message) => (
            <IonItem>
              <IonLabel>
                {message.username}: {message.text}
              </IonLabel>
            </IonItem>
          ))}
        </IonList>
      </IonContent>
    </IonPage>
  );
};

export default Rooms;
