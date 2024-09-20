import {
  IonButton,
  IonContent,
  IonHeader,
  IonInput,
  IonItem,
  IonLabel,
  IonLoading,
  IonPage,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import ExploreContainer from "../components/ExploreContainer";
import "./Home.css";
import {
  Question,
  cacheQuestions,
  getQuestion,
  postIdAndGetQuestions,
} from "../data/question";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Auth: React.FC = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState<string>("");
  const [questionsIds, setQuestionsIds] = useState<any[]>([]);
  const [questions, setQuestions] = useState<Question[]>([]);
  const [totalQuestionsNb, setTotalQuestionsNb] = useState<number>(0);
  const [loadedItems, setLoadedItems] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [hasError, setHasError] = useState<boolean>(false);

  const handle_login = async () => {
    const response = await postIdAndGetQuestions(input);
    console.log(response);

    setTotalQuestionsNb(response.questionIds.length);
    setQuestionsIds(response.questionIds);
    setLoadedItems(0);

    let currentQuestions: Question[] = questions;
    setLoading(true);
    let ok = true;
    for (const questionId of response.questionIds) {
      try {
        const randomNumber = Math.floor(Math.random() * 4) % 4;
        if (randomNumber == 0) {
          throw new Error("Random error");
        }
        //console.log("plm");
        //console.log("questionId: ", questionId);
        currentQuestions = await downloadQuestion(questionId, currentQuestions);
        //console.log("current questions: ", currentQuestions);
      } catch {
        ok = false;
        setHasError(true);
        //console.log("error");
      }
    }
    if (ok === true) {
      cacheQuestions(currentQuestions);
      navigate("/questions");
    }
    setLoading(false);
  };

  const downloadQuestion = async (
    questionId: number,
    currentQuestions: Question[]
  ) => {
    console.log("question id download", questionId);
    const question = await getQuestion(questionId);
    console.log("question", question);
    setQuestions((questions) => [...questions, question]);
    setLoadedItems((count) => count + 1);
    return [...currentQuestions, question];
  };

  const retry = async () => {
    setHasError(false);
    setLoading(true);
    let ok = true;
    let currentQuestions: Question[] = questions;
    for (let i = loadedItems; i < totalQuestionsNb; i++) {
      try {
        if (questionsIds)
          currentQuestions = await downloadQuestion(
            questionsIds[i],
            currentQuestions
          );
      } catch (e) {
        ok = false;
        setHasError(true);
        break;
      }
    }
    if (ok === true) {
      cacheQuestions(currentQuestions);
      navigate("/questions");
    }
    setLoading(false);
  };

  return (
    <>
      {loading === false && hasError === false ? (
        <IonPage>
          <IonHeader>
            <IonToolbar>
              <IonTitle>AUTHENTIFICATION</IonTitle>
            </IonToolbar>
          </IonHeader>
          <IonContent fullscreen>
            <IonHeader collapse="condense">
              <IonToolbar>
                <IonTitle size="large">CONECTEAZA-TE</IonTitle>
              </IonToolbar>
            </IonHeader>
            <IonItem>
              <IonInput
                onIonInput={(event: any) => {
                  setInput(event.target.value);
                }}
              ></IonInput>
              <IonButton onClick={handle_login}>connect with your id</IonButton>
            </IonItem>
          </IonContent>
        </IonPage>
      ) : (
        <>
          {hasError === false ? (
            <IonLoading
              message={`DOWNLOADING ${loadedItems}/${totalQuestionsNb}`}
              duration={0}
              isOpen={true}
            />
          ) : (
            <>
              <IonLabel>
                ERROR! Finised downloading {loadedItems} out of{" "}
                {totalQuestionsNb} questions.
              </IonLabel>
              <IonButton onClick={retry}>RETRY</IonButton>
            </>
          )}
        </>
      )}
    </>
  );
};

export default Auth;
