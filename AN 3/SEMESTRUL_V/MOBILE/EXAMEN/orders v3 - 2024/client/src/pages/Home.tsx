import {
  IonButton,
  IonContent,
  IonHeader,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonLoading,
  IonPage,
  IonRadio,
  IonRadioGroup,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import "./Home.css";
import { Menu, cacheMenu, getCachedMenu } from "../data/menu";
import { useEffect, useState } from "react";
import { cacheOrder, getCachedOrder, postOrder } from "../data/order";
import { ToastContainer, toast } from "react-toastify";

export interface Home {
  sentMessage: Menu[] | undefined;
}

const Home: React.FC<Home> = ({ sentMessage }) => {
  const [loading, setLoading] = useState<boolean>();
  const [menu, setMenu] = useState<Menu[]>([]);
  const [menuItemQuantity, setMenuItemQuantity] = useState<number[]>([]);
  const [fullMenuItemQuantity, setFullMenuItemQuantity] = useState<number[]>(
    []
  );
  const [filteredMenuItemQuantity, setFilteredMenuItemQuantity] = useState<
    number[]
  >([]);
  const [selectedItem, setSelectedItem] = useState<Menu>();
  const [fullMenu, setFullMenu] = useState<Menu[]>([]);
  const [filteredMenu, setFilteredMenu] = useState<Menu[]>([]);
  const [color, setColor] = useState<number[]>([]);
  const [loadingOrders, setLoadingOrders] = useState<boolean>(false);

  useEffect(() => {
    const cachedMenu = getCachedMenu();
    console.log("am luat din cache: ", cachedMenu);
    if (cachedMenu && cachedMenu.length > 0) {
      setMenu(cachedMenu);
      setFullMenu(cachedMenu);

      const myCachedOrder = getCachedOrder();
      const justCodes = myCachedOrder?.map((item) => item.code);

      for (let i = 0; i < cachedMenu.length; i++) {
        if (justCodes?.includes(cachedMenu[i].code)) {
          menuItemQuantity.push(
            myCachedOrder?.find((item) => item.code === cachedMenu[i].code)
              ?.quantity!
          );
        } else {
          menuItemQuantity.push(0);
        }
      }

      setFullMenuItemQuantity(menuItemQuantity);

      //vreau sa fac si filtrarea daca am order la inceput in cache
      //fac un filter pe rezultatul de sus cu cele ce n-au 0 la cantitate
      const filteredMenu = cachedMenu.filter(
        (item, idx) => menuItemQuantity[idx] !== 0
      );
      setFilteredMenu(filteredMenu);
      const filteredQuantity = menuItemQuantity.filter((item) => item !== 0);
      setFilteredMenuItemQuantity(filteredQuantity);

      setLoading(false);
    } else {
      setLoading(true);
    }
  }, []);

  useEffect(() => {
    if (sentMessage) {
      cacheMenu(sentMessage);
      setFullMenu(sentMessage);
      setMenu(sentMessage);

      for (let i = 0; i < sentMessage.length; i++) {
        menuItemQuantity.push(0);
      }
      setFullMenuItemQuantity(menuItemQuantity);

      setLoading(false);
    }
    console.log("sentMessage: ", sentMessage);
  }, [sentMessage]);

  const handleChangeQuantity = (quantity: any, index: number) => {
    if (!isNaN(parseInt(quantity, 10))) {
      fullMenuItemQuantity[index] = Number(quantity);
    } else {
      fullMenuItemQuantity[index] = quantity;
    }

    const filtered = fullMenu.filter(
      (item, idx) => fullMenuItemQuantity[idx] !== 0
    );
    console.log("IN HANDLE CHANGE QUANTITY -> filtered: ", filtered);
    console.log("IN HANDLE CHANGE QUANTITY -> quantity: ", menuItemQuantity);

    setFilteredMenu(filtered);

    const filteredQuantity = fullMenuItemQuantity.filter((item) => item !== 0);
    setFilteredMenuItemQuantity(
      fullMenuItemQuantity.filter((item) => item !== 0)
    );

    const order = filtered.map((item, idx) => {
      return {
        code: item.code,
        quantity: filteredQuantity[idx],
      };
    });
    cacheOrder(order);
  };

  return (
    <IonPage>
      <style>
        {`
          .error-item {
            color: red;
          }
        `}
      </style>
      <IonHeader>
        <IonToolbar>
          <IonTitle>MENU</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        {loading === true ? (
          <IonLoading message={`LOADING...`} duration={0} isOpen={true} />
        ) : (
          <></>
        )}
        <IonItem>
          <IonButton
            onClick={() => {
              setMenu(fullMenu);
              setMenuItemQuantity(fullMenuItemQuantity);
            }}
          >
            FULL MENU
          </IonButton>
          <IonButton
            onClick={() => {
              setMenu(filteredMenu);
              setMenuItemQuantity(filteredMenuItemQuantity);
            }}
          >
            FILTERED MENU
          </IonButton>
        </IonItem>
        <IonList>
          <IonRadioGroup
            onIonChange={(ev) => {
              const value = ev.target.value;
              setSelectedItem(value);
              console.log("selected item in onIonChange", value);
            }}
          >
            {menu.map((item, index) => (
              <IonItem className={color.includes(index) ? "error-item" : ""}>
                <IonRadio value={item}>
                  {item.name} - Quantity: {menuItemQuantity[index]}
                  {selectedItem === item ? (
                    <IonInput
                      label="Quantity"
                      onIonInput={(e) =>
                        handleChangeQuantity(e.target.value, index)
                      }
                    ></IonInput>
                  ) : (
                    ""
                  )}
                </IonRadio>
                <br></br>
              </IonItem>
            ))}
          </IonRadioGroup>
        </IonList>
        <IonButton
          onClick={async () => {
            const de_salvat = getCachedOrder();
            const colors_de_colorat: number[] = [];

            setLoadingOrders(true);
            for (let i = 0; i < de_salvat.length; i++) {
              try {
                if (navigator.onLine) {
                  const response = await postOrder(de_salvat[i]);
                } else {
                  toast("NU ESTI ONLINE");
                }
              } catch {
                const index = menu.findIndex(
                  (item) => item.code === de_salvat[i].code
                );
                colors_de_colorat.push(index);
                console.log("eroare la POST");
                toast("NU TI-E BINE CANTITATEA CEVA");
              }
            }
            setLoadingOrders(false);
            setColor(colors_de_colorat);
          }}
        >
          SUBMIT
        </IonButton>
      </IonContent>
      {loadingOrders === true ? (
        <IonLoading isOpen={true} message={`LOADING...`}></IonLoading>
      ) : (
        ""
      )}
    </IonPage>
  );
};

export default Home;
