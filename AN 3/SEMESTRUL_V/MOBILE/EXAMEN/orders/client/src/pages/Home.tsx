import {
  IonAlert,
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
import { auth, cacheToken, getCachedToken } from "../data/user";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {
  const [input, setInput] = useState<number>(0);
  const [hasError, setHasError] = useState<boolean>(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = getCachedToken();
    if (token) {
      navigate("/menu_screen");
    }
  });

  const handleSubmit = async () => {
    try {
      console.log("IN SUBMIT: ", input);
      const token = await auth(input);
      console.log(token);
      cacheToken(token);
      navigate("/menu_screen");
    } catch {
      setHasError(true);
      toast("TABEL NOT FREE, TRY AGAIN");
    }
  };
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>CHOOSE TABLE PAGE</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">CHOOSE TABLE</IonTitle>
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
            <IonButton onClick={handleSubmit}>START ORDER</IonButton>
          )}
        </>
      </IonContent>
      <ToastContainer />
    </IonPage>
  );
};

export default Home;
