// AddQuiz.js
import React, { useState } from "react";
import Question from "./Question"; // Import the Question component
import Navbar from "./NavBar"; // Import the Navbar component
import Button from "./Button"; // Import the Button component

const AddQuiz = () => {
  const [quizTitle, setQuizTitle] = useState("");
  const [questions, setQuestions] = useState([{ id: 1 }]);

  const handleAddQuestion = () => {
    if (questions.length < 10) {
      setQuestions((prevQuestions) => [
        ...prevQuestions,
        { id: prevQuestions.length + 1 },
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

  const handleSubmit = () => {
    // Perform submit logic here
    console.log("Quiz submitted:", { quizTitle, questions });
  };

  // Check if there is at least one question to enable the Submit button
  const isSubmitDisabled = questions.length < 1;

  return (
    <div className="min-h-screen">
      <Navbar currentPage="add-quiz" />

      <div className="flex flex-col items-center justify-center text-center">
        <h1 className="text-5xl font-handwriting text-orange mt-5">Add Quiz</h1>

        {/* Title Input */}
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
        {questions.map((question, index) => (
          <Question
            key={question.id}
            id={question.id}
            questionNumber={index + 1}
            onDelete={handleDeleteQuestion}
          />
        ))}

        {/* Use the custom Button component */}
        <Button
          onClick={handleAddQuestion}
          disabled={questions.length >= 10}
          customClass="mt-5"
        >
          Add Question
        </Button>

        {/* Submit Button */}
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
