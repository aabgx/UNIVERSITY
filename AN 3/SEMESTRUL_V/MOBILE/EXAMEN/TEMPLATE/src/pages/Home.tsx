import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import "./Home.css";
import { Item } from "../data/item";
import { useEffect, useState } from "react";
import { useConnection } from "../notifications/connection";
import { toast } from "react-toastify";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export interface Home {
  sentMessage: Item | undefined;
}
const Home: React.FC<Home> = ({ sentMessage }) => {
  //PT HANDLE ONLINE/OFFLINE
  const [isOnline, setIsOnline] = useConnection();
  const [labelOnline, setLabelOnline] = useState(false);
  useEffect(() => {
    if (!isOnline) {
      setLabelOnline(false);
    } else {
      setLabelOnline(true);
    }
  }, [isOnline]);

  //PT NOTIFICARI PRIN SOCKET
  useEffect(() => {
    toast(sentMessage?.name);
  }, [sentMessage]);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>PAGE TITLE</IonTitle>
          {labelOnline ? (
            <IonTitle>ONLINE</IonTitle>
          ) : (
            <IonTitle>OFFLINE</IonTitle>
          )}
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen></IonContent>
      <ToastContainer />
    </IonPage>
  );
};

export default Home;
