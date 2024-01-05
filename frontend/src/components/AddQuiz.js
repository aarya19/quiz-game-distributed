import React, { useState } from "react";
import Question from "./Question";
import Navbar from "./NavBar";
import Button from "./Button";
import { useParams, useNavigate } from "react-router-dom";

import { v4 as uuidv4 } from "uuid";

const AddQuiz = () => {
  const [quizTitle, setQuizTitle] = useState("");
  const [questions, setQuestions] = useState([{ id: 1 }]);
  const { username } = useParams();
  const navigate = useNavigate();
  const [quizID, setQuizID] = useState(uuidv4());

  const handleAddQuestion = () => {
    if (questions.length < 10) {
      setQuestions((prevQuestions) => [
        ...prevQuestions,
        { id: prevQuestions.length + 1, options: [] },
      ]);
    }
  };

  const handleTitleChange = (e) => {
    setQuizTitle(e.target.value);
  };

  const handleDeleteQuestion = (deletedQuestionId) => {
    const updatedQuestions = questions.filter(
      (question) => question.id !== deletedQuestionId
    );
    setQuestions(updatedQuestions);
  };

  const handleQuestionChange = (questionId, updatedQuestion) => {
    const updatedQuestions = questions.map((question) =>
      question.id === questionId ? updatedQuestion : question
    );
    setQuestions(updatedQuestions);
  };

  const handleSubmit = async () => {
    try {
      // Validation checks
      if (
        quizTitle.trim() === "" ||
        questions.some(
          (q) =>
            q.question.trim() === "" ||
            Object.values(q.options).some((opt) => opt.trim() === "") ||
            q.correctAnswer === undefined ||
            q.correctAnswer.trim() === "" ||
            !["OptionA", "OptionB", "OptionC", "OptionD"].includes(
              q.correctAnswer
            )
        )
      ) {
        alert(
          "Please fill in all fields for each question and make sure to select the correct option."
        );
        return;
      }

      console.log("Validation passed. Submitting quiz...");

      // Log all questions, options, and correct answers
      questions.forEach((q) => {
        console.log("Question:", q.question);
        console.log("Options:", q.options);
        console.log("Correct Answer:", q.correctAnswer);
      });

      const quizData = {
        userName: username,
        quizID,
        quizTitle,
        questions: questions.map(
          ({ id, question, options, correctAnswer }) => ({
            id: id,
            question,
            options,
            correctAnswer,
            quizId: quizID,
          })
        ),
      };

      console.log("Quiz data:", quizData);

      const response = await fetch(
        `http://localhost:8080/app/createQuiz/${username}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(quizData),
        }
      );

      if (response.ok) {
        const responseBody = await response.text();
        console.log("Quiz submitted successfully:", responseBody);
        alert("Quiz creation successful!");
        navigate(`/${username}/quizmaster`);
      } else {
        console.error("Quiz submission failed with status:", response.status);
        alert("Quiz submission failed. Please try again.");
      }
    } catch (error) {
      console.error("Error during quiz submission:", error.message);
      alert(
        "An unexpected error occurred during quiz submission. Please try again later."
      );
    }
  };

  const isSubmitDisabled = questions.length < 1;

  return (
    <div className="min-h-screen">
      <Navbar currentPage="add-quiz" />
      <div className="flex flex-col items-center justify-center text-center">
        <h1 className="text-5xl font-handwriting text-orange mt-5">Add Quiz</h1>
        <label htmlFor="quizTitle" className="text-2xl mt-5">
          Quiz Title:
        </label>
        <input
          type="text"
          id="quizTitle"
          value={quizTitle}
          onChange={handleTitleChange}
          placeholder="Enter quiz title"
          className="border-2 border-b2 rounded-lg px-5 mx-5 py-2 mt-5"
        />
        <h3 className="text-3xl font-bold mt-5">Questions</h3>
        {questions.map((question) => (
          <Question
            key={question.id}
            id={question.id}
            questionNumber={question.id}
            onDelete={handleDeleteQuestion}
            onChange={(updatedQuestion) =>
              handleQuestionChange(question.id, updatedQuestion)
            }
          />
        ))}
        <Button
          onClick={handleAddQuestion}
          disabled={questions.length >= 10}
          customClass="mt-5"
        >
          Add Question
        </Button>
        <Button
          onClick={handleSubmit}
          disabled={isSubmitDisabled}
          customClass={`mt-5 bg-green-500 text-white ${
            isSubmitDisabled ? "cursor-not-allowed" : ""
          }`}
        >
          Submit
        </Button>
      </div>
    </div>
  );
};

export default AddQuiz;
