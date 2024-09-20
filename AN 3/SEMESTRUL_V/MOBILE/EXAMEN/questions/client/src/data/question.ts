import axios from "axios";
import { User } from "./user";

export interface Question {
  id: number;
  text: string;
  options: string[];
  indexCorrectOption: number;
}
export interface QuestionIds {
  token: String;
  questionIds: number[];
}

export const postIdAndGetQuestions = async (id: string) => {
  const user: User = { id };
  console.log("user", user);
  const response = await axios.post<QuestionIds>(
    `http://localhost:3000/auth`,
    user
  );
  return response.data;
};

export const getQuestion = async (id: number) => {
  const response = await axios.get(`http://localhost:3000/question/${id}`);
  return response.data;
};

//pt cache intrebari
export const cacheQuestions = async (question: Question[]) => {
  localStorage.setItem("questions", JSON.stringify(question));
};
export const getCachedQuestions = (): Question[] => {
  const questions = localStorage.getItem("questions");
  if (questions === null) {
    return [];
  }
  return JSON.parse(questions);
};

//pt cache raspunsuri
export const cacheAnswers = async (answer: number[]) => {
  localStorage.setItem("answers", JSON.stringify(answer));
};
export const getCachedAnswers = (): number[] => {
  const answers = localStorage.getItem("answers");
  if (answers === null) {
    return [];
  }
  return JSON.parse(answers);
};
