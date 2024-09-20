import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { IonApp, IonRouterOutlet, setupIonicReact } from "@ionic/react";
import { IonReactRouter } from "@ionic/react-router";
import Home from "./pages/Home";

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
import { useEffect, useState } from "react";
import { connect } from "./notification/notifications";
import { Menu, getCachedMenu } from "./data/menu";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useConnection } from "./notification/connection";

setupIonicReact();

const App: React.FC = () => {
  const [isOnline, setIsOnline] = useConnection();

  const [sentMessage, setSentMessage] = useState<Menu[]>();
  useEffect(() => {
    const cachedMenu = getCachedMenu();
    console.log("lennn: ", cachedMenu.length);
    if (cachedMenu.length === 0) {
      console.log("a intrat in WS");
      const webSocket = connect(setSentMessage);
      return () => {
        if (webSocket.readyState === WebSocket.OPEN) {
          webSocket.close();
        }
      };
    }
  }, []);

  useEffect(() => {
    if (!isOnline) {
      toast("NU MAI ESTI ONLINE");
    }
  }, [isOnline]);
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/home" />} />
          <Route
            path="/home"
            element={<Home sentMessage={sentMessage} />}
          ></Route>
        </Routes>
      </BrowserRouter>
      <ToastContainer />
    </>
  );
};

export default App;
