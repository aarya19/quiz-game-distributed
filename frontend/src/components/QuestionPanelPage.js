import React from "react";
import CurrentQuestion from "./CurrentQuestion";
import Navbar from "./NavBar";

const QuestionPanelPage = () => {
  const sampleQuestion = {
    id: "1",
    question: "What is the capital of Canada?",
    quizId: "quiz123",
    options: {
      A: "Toronto",
      B: "Ottawa",
      C: "Vancouver",
      D: "Montreal",
    },
    correctAnswer: "B",
  };

  const handleSelectOption = (selectedOption) => {
    // Handle the selected option logic here
    console.log("Selected Option:", selectedOption);
  };

  return (
    <div>
      <Navbar currentPage="game-page" />
      <div className="container mx-auto mt-20 flex items-center justify-center">
        <CurrentQuestion
          question={sampleQuestion}
          onSelectOption={handleSelectOption}
        />
      </div>
    </div>
  );
};

export default QuestionPanelPage;
