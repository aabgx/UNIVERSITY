import {
  IonAlert,
  IonButton,
  IonContent,
  IonHeader,
  IonInput,
  IonItem,
  IonList,
  IonPage,
  IonRadio,
  IonRadioGroup,
  IonSearchbar,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import ExploreContainer from "../components/ExploreContainer";
import "./Home.css";
import { useEffect, useState } from "react";
import { auth, cacheToken, getCachedToken } from "../data/user";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Menu, getMenuItems } from "../data/menu";
import { Order, cacheOrders, getCachedOrders, saveOrder } from "../data/order";
import "react-toastify/dist/ReactToastify.css";

export interface MenuScreen {
  sentMessage: Menu | undefined;
}
const MenuScreen: React.FC<MenuScreen> = ({ sentMessage }) => {
  const [searchTimeout, setSearchTimeout] = useState<number | null>(null);
  const [show, setShow] = useState(false);
  const [menuFound, setMenuFound] = useState<Menu[]>([]);
  const [quantity, setQuantity] = useState<number>(0);
  const [selectedItem, setSelectedItem] = useState<number>(0);
  const [anyAnswerSelected, setAnyAnswerSelected] = useState(false);
  const [hasError, setHasError] = useState(false);
  const [orderItems, setOrderItems] = useState<Order[]>([]);

  useEffect(() => {
    const orders = getCachedOrders(Number(getCachedToken()));

    let goodOrders = [];
    for (let i = 0; i < orders.length; i++) {
      const o: Order = {
        code: orders[i].code,
        quantity: orders[i].quantity,
        table: Number(getCachedToken()),
        free: orders[i].free,
      };
      goodOrders.push(o);
    }

    console.log("tokennnnnn: " + getCachedToken());
    console.log("ORDERS: ", goodOrders);
    setOrderItems(goodOrders);
  }, []);

  const handleSearchChange = (value: string) => {
    setShow(false);
    if (searchTimeout !== null) {
      clearTimeout(searchTimeout);
    }
    setSearchTimeout(
      window.setTimeout(async () => {
        console.log("Valoarea după 2 secunde de inactivitate:", value);
        const menuItems = await getMenuItems(value);

        const five = [];
        for (let i = 0; i < 5; i++) {
          five.push(menuItems[i]);
        }

        setMenuFound(five);
        console.log(menuItems);
        if (menuItems.length > 0) {
          setShow(true);
        }
      }, 2000)
    );
  };

  const handleAddToCart = async () => {
    try {
      setHasError(false);
      const randomNumber = Math.floor(Math.random() * 4) % 4;
      if (randomNumber == 0) {
        throw new Error("Random error");
      }
      console.log("ADD TO CART");
      console.log("selected item: ", selectedItem);
      const response = await saveOrder(selectedItem, quantity, false);
      setOrderItems([...orderItems, response]);

      //salvare in cache
      cacheOrders([...orderItems, response]);

      console.log(response);
    } catch {
      setHasError(true);
      toast("ERROR ADD TO ORDER");
    }
  };

  const retry = async () => {
    setHasError(false);
    const response = await saveOrder(selectedItem, quantity, false);
    setOrderItems([...orderItems, response]);
    cacheOrders([...orderItems, response]);
    console.log(response);
  };

  useEffect(() => {
    toast(sentMessage?.name, {
      onClick: async () => {
        console.log("clicked");
        const response = await saveOrder(sentMessage?.code ?? 0, 1, true);
        setOrderItems([...orderItems, response]);
        cacheOrders([...orderItems, response]);
      },
    });
  }, [sentMessage]);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>ORDER PAGE</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonSearchbar
          onIonInput={(e: any) => {
            handleSearchChange(e.target.value);
          }}
        ></IonSearchbar>
        {show ? (
          <>
            <IonRadioGroup
              onIonChange={(ev) => {
                const value = ev.detail.value;
                setSelectedItem(value);
                setAnyAnswerSelected(true);
              }}
            >
              {menuFound.map((menuItem) => (
                <>
                  <IonItem>
                    <IonRadio value={menuItem.code}>{menuItem.name}</IonRadio>
                    <br></br>
                  </IonItem>
                </>
              ))}
            </IonRadioGroup>
            <IonInput
              onIonInput={(e: any) => {
                setQuantity(e.target.value);
              }}
            >
              add quantity
            </IonInput>
            {anyAnswerSelected &&
              quantity &&
              (hasError ? (
                <IonButton onClick={retry}>RETRY</IonButton>
              ) : (
                <IonButton onClick={handleAddToCart}>ADD TO ORDER</IonButton>
              ))}
          </>
        ) : (
          ""
        )}
        <IonList>
          {orderItems.map((orderItem) => (
            <IonItem>
              item code: {orderItem.code} - quantity: {orderItem.quantity}
            </IonItem>
          ))}
        </IonList>
      </IonContent>
    </IonPage>
  );
};

export default MenuScreen;
