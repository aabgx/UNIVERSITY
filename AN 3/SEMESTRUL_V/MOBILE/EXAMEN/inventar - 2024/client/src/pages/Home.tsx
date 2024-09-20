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
  IonSearchbar,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import "./Home.css";
import {
  Item,
  cacheItems,
  cacheItemsNecomandate,
  getCachedItems,
  postItem,
} from "../data/item";
import { useEffect, useState } from "react";
import { useConnection } from "../notifications/connection";
import { toast } from "react-toastify";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  Product,
  ProductResponse,
  getAllCachedProducts,
  getProducts,
} from "../data/product";
import { all } from "axios";
import { getCachedItemsNecomandate } from "../data/item";

export interface Home {
  sentMessage: Product | undefined;
}
const Home: React.FC<Home> = ({ sentMessage }) => {
  const [loading, setLoading] = useState(false);
  const [currentPageToDownload, setCurrentPageToDownload] = useState(0);
  const [allDownloadedPages, setAllDownloadedPages] = useState<
    (ProductResponse | null | undefined)[]
  >([]);
  const [error, setError] = useState(false);
  const [searchTResults, setSearchResults] = useState<Product[]>([]);
  const [searchTimeout, setSearchTimeout] = useState<number | null>(null);
  const [show, setShow] = useState(false);
  const [selectedItem, setSelectedItem] = useState<number>(0);
  const [anyAnswerSelected, setAnyAnswerSelected] = useState(false);
  const [quantityUI, setQuantityUI] = useState<number>(0);
  const [hasError, setHasError] = useState(false);

  //elementele adaugate
  const [orderItems, setOrderItems] = useState<Item[]>([]);
  //pt upload
  const [loadingUpload, setLoadingUpload] = useState(false);

  useEffect(() => {
    if (navigator.onLine) {
      fetchData();
    }
    setOrderItems(getCachedItemsNecomandate());
  }, []);
  const fetchData = async () => {
    setLoading(true);
    const allResponses: ProductResponse[] = [];
    let totalNumberOfPages = 1;
    for (let page = 0; page < totalNumberOfPages; page++) {
      setCurrentPageToDownload(page + 1);
      try {
        // if (page == 3) {
        //   throw new Error("Error");
        // }
        const response = await getProducts(page);
        allResponses.push(response);

        if (page == 0) {
          totalNumberOfPages = response.total / 10;
          setTotalNumberOfPages(totalNumberOfPages);
          setLoading(true);
        }
      } catch {
        setError(true);
        toast.error(`Error downloading page ${page + 1} `);
        break;
      }
      setAllDownloadedPages(allResponses);
      console.log(allResponses);
    }
    setLoading(false);
  };
  const [totalNumberOfPages, setTotalNumberOfPages] = useState(0);
  const [allNotificationsRecived, setAllNotificationsRecived] =
    useState<number>(0);

  useEffect(() => {
    setError(allDownloadedPages.filter((page) => page === null).length > 0);
  }, [allDownloadedPages]);

  const handleRetry = async (page: number) => {
    setLoading(true);
    setCurrentPageToDownload(page + 1);
    const productsResponse = await getProducts(page);
    setAllDownloadedPages((prev) => {
      const newPages = [...prev];
      newPages[page] = productsResponse;
      return newPages;
    });
    setLoading(false);
  };

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
    if (sentMessage) {
      toast(sentMessage?.code + " " + sentMessage?.name);
      setAllNotificationsRecived((prev) => prev + 1);
    }
  }, [sentMessage]);

  const handleSearchChange = (value: string) => {
    setShow(false);
    if (searchTimeout !== null) {
      clearTimeout(searchTimeout);
    }
    setSearchTimeout(
      window.setTimeout(async () => {
        const de_afisat = allDownloadedPages
          .filter((page) => page !== null && page !== undefined)
          .map((page) => page!.products)
          .flat()
          .filter((product) =>
            product.name.toLowerCase().includes(value.toLowerCase())
          )
          .slice(0, 5);
        setSearchResults(de_afisat);
        if (de_afisat.length > 0) {
          setShow(true);
        }
      }, 2000)
    );
  };

  const handleAddToCart = () => {
    try {
      const code = selectedItem;
      const quantity = quantityUI;
      const order: Item = { code, quantity };

      setOrderItems([...orderItems, order]);
      cacheItemsNecomandate([...orderItems, order]);
    } catch {
      setHasError(true);
      toast("ERROR ADD TO ORDER");
    }
  };
  const retryHandleAddToCart = () => {};
  const handleSubmit = async () => {
    setLoadingUpload(true);
    try {
      for (let i = 0; i < orderItems.length; i++) {
        const response = await postItem(orderItems[i]);
        console.log(response);
      }
      toast("COMANDA TRIMISA");
      const alreadyCached = getCachedItems();
      alreadyCached.push(...orderItems);
      cacheItemsNecomandate([]);
      cacheItems(alreadyCached);
      setOrderItems([]);
    } catch {
      toast("ERROR UPLOADING");
    }

    setLoadingUpload(false);
    // const alreadyCached = getCachedItems();
    // alreadyCached.push(...orderItems);
    // cacheItemsNecomandate([]);
    // cacheItems(alreadyCached);
    // setOrderItems([]);
    // setLoadingUpload(false);
  };

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
      <IonContent fullscreen>
        {error === true ? (
          <IonButton
            onClick={() => {
              allDownloadedPages.map((_, index) => {
                if (allDownloadedPages[index] === null) {
                  handleRetry(index);
                }
              });
            }}
          >
            <IonLabel>SOMETHING WENT WRONG! RETRY</IonLabel>
          </IonButton>
        ) : (
          <>
            <IonSearchbar
              onIonInput={(e: any) => {
                handleSearchChange(e.target.value);
              }}
            ></IonSearchbar>
            <>
              {show ? (
                <>
                  <IonRadioGroup
                    onIonChange={(ev) => {
                      const value = ev.detail.value;
                      setSelectedItem(value);
                      setAnyAnswerSelected(true);
                    }}
                  >
                    {searchTResults.map((menuItem) => (
                      <>
                        <IonItem>
                          <IonRadio value={menuItem.code}>
                            {menuItem.name}
                          </IonRadio>
                          <br></br>
                        </IonItem>
                      </>
                    ))}
                  </IonRadioGroup>
                  <IonInput
                    label={"quantity"}
                    onIonInput={(e: any) => {
                      setQuantityUI(e.target.value);
                    }}
                  ></IonInput>
                  {anyAnswerSelected &&
                    quantityUI &&
                    (hasError ? (
                      <IonButton onClick={retryHandleAddToCart}>
                        RETRY
                      </IonButton>
                    ) : (
                      <IonButton onClick={handleAddToCart}>
                        ADD TO ORDER
                      </IonButton>
                    ))}
                </>
              ) : (
                ""
              )}
            </>
          </>
        )}
        <IonList>
          {orderItems.map((orderItem) => (
            <IonItem>
              item code: {orderItem.code} - quantity: {orderItem.quantity}
            </IonItem>
          ))}
        </IonList>
        {loading && (
          <IonLoading
            isOpen={true}
            // message={`downloading ${currentPageToDownload} / ${
            //   allDownloadedPages.length < 10 ? 10 : allDownloadedPages.length
            // }`}
            message={`${currentPageToDownload} / ${totalNumberOfPages}`}
          ></IonLoading>
        )}
        <IonButton onClick={handleSubmit}>TRIMITE COMANDA</IonButton>
        {loadingUpload && (
          <IonLoading isOpen={true} message={`uploading order`}></IonLoading>
        )}
        {!error && allNotificationsRecived > 0 && (
          <IonButton onClick={fetchData}>DOWNLOAD ALL AGAIN</IonButton>
        )}
      </IonContent>
      <ToastContainer />
    </IonPage>
  );
};

export default Home;
