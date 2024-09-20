import { BrowserRouter, Route, Navigate, Routes } from "react-router-dom";
import { IonApp, IonRouterOutlet, setupIonicReact } from "@ionic/react";
import { IonReactRouter } from "@ionic/react-router";

/* Core CSS required for Ionic components to work properly */
import "@ionic/react/css/core.css";

/* Basic CSS for apps built with Ionic */
import "@ionic/react/css/normalize.css";
import "@ionic/react/css/structure.css";
import "@ionic/react/css/typography.css";

/* Optional CSS utils that can be commented out */
import "@ionic/react/css/padding.css";
import "@ionic/react/css/float-elements.css";
import "@ionic/react/css/text-alignment.css";
import "@ionic/react/css/text-transformation.css";
import "@ionic/react/css/flex-utils.css";
import "@ionic/react/css/display.css";

/* Theme variables */
import "./theme/variables.css";
import Auth from "./pages/Auth";
import Questions from "./pages/Questions";
import { useEffect, useState } from "react";
import { Question } from "./data/question";
import { connect } from "./notifications/notifications";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

setupIonicReact();

const App: React.FC = () => {
  const [sentMessage, setSentMessage] = useState<Question>();
  useEffect(() => {
    const webSocket = connect(setSentMessage);
    return () => {
      webSocket.close();
    };
  }, []);
  useEffect(() => {
    toast(sentMessage?.text);
  }, [sentMessage]);

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/auth" />} />
          <Route path="/auth" element={<Auth />}></Route>
          <Route
            path="/questions"
            element={<Questions sentMessage={sentMessage} />}
          ></Route>
        </Routes>
      </BrowserRouter>
      <ToastContainer />
    </>
  );
};

export default App;
