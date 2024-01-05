import React, { useState, useEffect } from "react";

const CurrentQuestion = ({ question, sendOption }) => {
  const [selectedOption, setSelectedOption] = useState(null);

  useEffect(() => {
    setSelectedOption(null);
  }, [question]);

  const handleOptionClick = (key) => {
    setSelectedOption(key); //to change the color on this component
    sendOption(key); //to change the state of QuestionPanelPage.
  };

  return (
    <div className="container mx-auto mt-8">
      {question ? (
        <div className="bg-gray-200 p-4 rounded-md text-center">
          <p className="text-lg font-bold">{question.question}</p>
          <div className="mt-4 space-y-4">
            {Object.entries(question.options).map(([key, value]) => (
              <div
                key={key}
                className={`${
                  selectedOption === key ? "bg-green-500" : "bg-blue-500"
                } p-4 rounded-md text-white cursor-pointer`}
                onClick={() => handleOptionClick(key)}
              >
                <p className="text-lg font-bold">
                  {key}: {value}
                </p>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="bg-gray-200 p-4 rounded-md text-center">
          <p className="text-lg font-bold">Waiting for incoming questions...</p>
        </div>
      )}
    </div>
  );
};

export default CurrentQuestion;
