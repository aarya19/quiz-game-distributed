import React, { useState } from "react";

const CurrentQuestion = ({ question, onSelectOption }) => {
  const [selectedOption, setSelectedOption] = useState(null);

  const handleOptionClick = (key) => {
    setSelectedOption(key);
    onSelectOption(key);
  };

  return (
    <div className="container mx-auto mt-8">
      <div className="bg-gray-200 p-4 rounded-md text-center">
        <p className="text-lg font-bold">{question.question}</p>
      </div>

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
  );
};

export default CurrentQuestion;
