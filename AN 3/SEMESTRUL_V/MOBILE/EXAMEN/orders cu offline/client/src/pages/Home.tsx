import {
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
import { Menu, cacheMenu, getCachedMenu, getMenuItems } from "../data/menu";
import { useConnection } from "../notification/connection";
import { useEffect, useState } from "react";
import {
  Order,
  cacheOrders,
  cacheOrdersDeSalvat,
  getCacheOrdersDeSalvat,
  getCachedOrders,
  saveOrder,
} from "../data/order";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export interface Home {
  sentMessage: Menu | undefined;
}

const Home: React.FC<Home> = ({ sentMessage }) => {
  const [isOnline, setIsOnline] = useConnection();
  const [labelOnline, setLabelOnline] = useState(true);

  const [searchTimeout, setSearchTimeout] = useState<number | null>(null);
  const [show, setShow] = useState(false);
  const [menuFound, setMenuFound] = useState<Menu[]>([]);
  const [quantity, setQuantity] = useState<number>(0);
  const [selectedItem, setSelectedItem] = useState<number>(0);
  const [anyAnswerSelected, setAnyAnswerSelected] = useState(false);
  const [hasError, setHasError] = useState(false);
  const [orderItems, setOrderItems] = useState<Order[]>([]);

  useEffect(() => {
    const orders = getCachedOrders();
    setOrderItems(orders);
  }, []);

  useEffect(() => {
    if (!isOnline) {
      setLabelOnline(false);
    } else {
      setLabelOnline(true);
      handleSaveBackOnline();
    }
  }, [isOnline]);

  const handleSaveBackOnline = async () => {
    const ordersDeSalvat = getCacheOrdersDeSalvat();
    console.log("DE SALVAT BACK ONLINE: ", ordersDeSalvat);
    if (ordersDeSalvat.length > 0) {
      for (let i = 0; i < ordersDeSalvat.length; i++) {
        console.log("SALVEZ ACUM: ", ordersDeSalvat[i]);
        const order = ordersDeSalvat[i];
        const response = await saveOrder(order.code, order.quantity, false);

        let orders = getCachedOrders();
        orders = [...orders, response];
        cacheOrders(orders);
        setOrderItems(orders);
        console.log(response);
      }
      //setOrderItems(getCachedOrders());
      cacheOrdersDeSalvat([]);
    }
  };

  const handleSearchChange = (value: string) => {
    setShow(false);
    if (searchTimeout !== null) {
      clearTimeout(searchTimeout);
    }
    setSearchTimeout(
      window.setTimeout(async () => {
        // console.log("Valoarea după 2 secunde de inactivitate:", value);
        if (navigator.onLine) {
          const menuItems = await getMenuItems(value);
          cacheMenu(menuItems);

          const five = [];
          for (let i = 0; i < 5; i++) {
            five.push(menuItems[i]);
          }

          setMenuFound(five);
          // console.log(menuItems);
          if (menuItems.length > 0) {
            setShow(true);
          }
        } else {
          const menuItems = getCachedMenu();

          const five = [];
          for (let i = 0; i < 5; i++) {
            five.push(menuItems[i]);
          }

          setMenuFound(five);
          // console.log(menuItems);
          if (menuItems.length > 0) {
            setShow(true);
          }
        }
      }, 2000)
    );
  };

  const handleAddToCart = async () => {
    try {
      if (navigator.onLine) {
        setHasError(false);
        const randomNumber = Math.floor(Math.random() * 4) % 4;
        if (randomNumber == 0) {
          throw new Error("Random error");
        }

        const response = await saveOrder(selectedItem, quantity, false);
        setOrderItems([...orderItems, response]);

        //salvare in cache
        cacheOrders([...orderItems, response]);
        // console.log(response);
      } else {
        const code = selectedItem;
        const free = false;
        const order: Order = { code, quantity, free };
        setOrderItems([...orderItems, order]);

        // cacheOrders([...orderItems, order]);
        let nesalvate = getCacheOrdersDeSalvat();
        nesalvate = [...nesalvate, order];
        cacheOrdersDeSalvat(nesalvate);
        console.log("DE SALVAT: ", getCacheOrdersDeSalvat());
      }
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
    // console.log(response);
  };

  useEffect(() => {
    toast(sentMessage?.name, {
      onClick: async () => {
        // console.log("clicked");
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
          <IonTitle>
            {labelOnline ? (
              <IonTitle size="large">ONLINE</IonTitle>
            ) : (
              <IonTitle size="large">OFFLINE</IonTitle>
            )}
          </IonTitle>
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
              label={"quantity"}
              onIonInput={(e: any) => {
                setQuantity(e.target.value);
              }}
            ></IonInput>
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
      <ToastContainer />
    </IonPage>
  );
};

export default Home;
