import {
  IonButton,
  IonContent,
  IonHeader,
  IonInput,
  IonPage,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import ExploreContainer from "../components/ExploreContainer";
import "./Home.css";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import { auth, cacheRooms, cacheToken, getCachedToken } from "../data/user";
import { getMessages, getRooms } from "../data/message";

const Home: React.FC = () => {
  const [input, setInput] = useState<string>("");
  const [hasError, setHasError] = useState<boolean>(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = getCachedToken();
    if (token) {
      navigate("/rooms");
    }
  });

  const handleSubmit = async () => {
    try {
      console.log("IN SUBMIT: ", input);
      const token = await auth(input);
      console.log("token: ", token);
      cacheToken(token);

      const response = await getMessages();
      const rooms_create = getRooms(response);
      cacheRooms(rooms_create);
      navigate("/rooms");
    } catch {
      setHasError(true);
      toast("USERNAME ALREADY IN USE");
    }
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>LOGIN PAGE</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Blank</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonInput
          onIonInput={(e: any) => {
            setInput(e.target.value);
            console.log(e.target.value);
          }}
        ></IonInput>
        <>
          {hasError ? (
            <>
              <IonButton onClick={handleSubmit}>RETRY</IonButton>
            </>
          ) : (
            <IonButton onClick={handleSubmit}>LOGIN</IonButton>
          )}
        </>
      </IonContent>
    </IonPage>
  );
};

export default Home;
