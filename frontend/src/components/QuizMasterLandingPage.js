// QuizMasterLandingPage.js
import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Navbar from "./NavBar";
import QuizList from "./QuizList";
import Button from "./Button";

const QuizMasterLandingPage = () => {
  const [quizzes, setQuizzes] = useState([]);
  const { username } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchQuizzes = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/app/fetchQuizzes/${username}`
        );
        if (!response.ok) {
          throw new Error(`Failed to fetch quizzes: ${response.statusText}`);
        }

        const data = await response.json();
        console.log("Quizzes JSON:", data);
        setQuizzes(data);
      } catch (error) {
        console.error("Error fetching quizzes:", error);
      }
    };

    fetchQuizzes();
  }, [username]);

  const handleStartQuiz = (quizId) => {
    navigate(`/${username}/startquiz/${quizId}`);
  };

  const handleAddNewQuiz = () => {
    navigate(`/${username}/addquiz`);
  };

  return (
    <div>
      <Navbar currentPage="quiz-master-landing" />
      <div className="container mx-auto mt-8">
        <h1 className="font-handwriting text-5xl">{`Welcome, ${username}!`}</h1>
        <Button onClick={handleAddNewQuiz} customClass="mt-5">
          Add New Quiz
        </Button>

        <QuizList quizzes={quizzes} onStartQuiz={handleStartQuiz} />
      </div>
    </div>
  );
};

export default QuizMasterLandingPage;
