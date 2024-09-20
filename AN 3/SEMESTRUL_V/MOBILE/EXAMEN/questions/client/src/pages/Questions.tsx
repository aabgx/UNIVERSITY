import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonContent,
  IonHeader,
  IonPage,
  IonRadio,
  IonRadioGroup,
  IonTitle,
  IonToolbar,
} from "@ionic/react";
import ExploreContainer from "../components/ExploreContainer";
import "./Home.css";
import { Question, cacheAnswers, getCachedQuestions } from "../data/question";
import { useEffect, useState } from "react";

export interface Questions {
  sentMessage: Question | undefined;
}

const Questions: React.FC<Questions> = ({ sentMessage }) => {
  const questionsChached = getCachedQuestions();
  const [questions, setQuestions] = useState<Question[]>(questionsChached);
  const [currentQuestion, setCurrentQuestion] = useState<Question>();
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState<number>(1);
  const [totalNumberOfQuestions, setTotalNumberOfQuestions] = useState<number>(
    questions.length
  );

  const [selectedValue, setSelectedValue] = useState<string>("");
  const [anyAnswerSelected, setAnyAnswerSelected] = useState<boolean>(false);
  const [correctAnswers, setCorrectAnswers] = useState<number>(0);

  const [answers, setAnswers] = useState<any[]>([]);

  useEffect(() => {
    setCurrentQuestion(questions[currentQuestionIndex - 1]);
    let ok = true;
    setInterval(() => {
      if (ok) submitAnswear();
    }, 3000);
    return () => {
      ok = false;
    };
  }, [currentQuestionIndex]);

  const submitAnswear = async () => {
    setAnswers((answers) => [...answers, selectedValue]);
    if (
      selectedValue ==
      currentQuestion?.options[currentQuestion.indexCorrectOption]
    ) {
      setCorrectAnswers((correctAnswers) => correctAnswers + 1);
    }
    setSelectedValue("");
    setAnyAnswerSelected(false);
    if (currentQuestionIndex <= totalNumberOfQuestions) {
      setCurrentQuestionIndex(
        (currentQuestionIndex) => currentQuestionIndex + 1
      );
    } else {
      await cacheAnswers(answers);
    }
  };
  return (
    <IonPage>
      <IonHeader>
        {currentQuestionIndex == totalNumberOfQuestions + 1 ? (
          "" //a terminat intrebarile, nu mai vr sa imi afiseze la ce intrebare e
        ) : (
          <p>
            Questions {currentQuestionIndex}/{totalNumberOfQuestions}
          </p>
        )}
        <p>
          Correct answers: {correctAnswers}/{totalNumberOfQuestions}
        </p>
      </IonHeader>

      <IonContent fullscreen>
        {currentQuestionIndex == totalNumberOfQuestions + 1 ? (
          "FINISHED ALL QUESTIONS!"
        ) : (
          <IonCard>
            {currentQuestion ? (
              <>
                <IonCardHeader>
                  <p>{currentQuestion?.text}</p>
                </IonCardHeader>
                <IonCardContent>
                  <IonRadioGroup
                    onIonChange={(ev) => {
                      const value = JSON.stringify(ev.detail.value);
                      setSelectedValue(value);
                      setAnyAnswerSelected(true);
                    }}
                  >
                    {currentQuestion.options.map((option) => (
                      <>
                        <IonRadio value={option}>{option}</IonRadio>
                        <br />
                      </>
                    ))}
                  </IonRadioGroup>
                  {anyAnswerSelected ? (
                    <IonButton onClick={submitAnswear}>
                      Go to the next question
                    </IonButton>
                  ) : (
                    ""
                  )}
                </IonCardContent>
              </>
            ) : (
              "LOADING......"
            )}
          </IonCard>
        )}
      </IonContent>
    </IonPage>
  );
};

export default Questions;
