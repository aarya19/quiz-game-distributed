// Question.js
import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";

function Question({ id, questionNumber, onDelete, onChange }) {
  const [question, setQuestion] = useState("");
  const [options, setOptions] = useState({
    OptionA: "",
    OptionB: "",
    OptionC: "",
    OptionD: "",
  });
  const [correctOption, setCorrectOption] = useState(""); // Initialize correctOption

  const handleOptionChange = (key, value) => {
    setOptions({
      ...options,
      [key]: value,
    });

    // Update the parent component with the changed question
    onChange({
      id,
      question,
      options: { ...options, [key]: value },
      correctAnswer: correctOption || key,
    });
  };

  const handleCorrectOptionChange = (key) => {
    setCorrectOption(key);

    // Update the parent component with the changed correctAnswer
    onChange({
      id,
      question,
      options,
      correctAnswer: key,
    });
  };

  const handleDeleteQuestion = () => {
    // Set correctOption to an empty string when deleting the question
    setCorrectOption("");
    onDelete(id);
  };

  return (
    <div className="flex flex-col mt-3 bg-gray-100 p-5 rounded-lg w-2/3 justify-center items-center">
      <TextField
        label={`Question ${questionNumber}`}
        type="text"
        placeholder={`Enter your question ${questionNumber}`}
        value={question}
        onChange={(e) => setQuestion(e.target.value)}
      />

      <div className="flex space-x-3">
        {Object.entries(options).map(([key, value]) => (
          <div key={key} className="flex flex-col items-center">
            <TextField
              label={`${key}`}
              type="text"
              placeholder={`Enter option ${key}`}
              value={value}
              onChange={(e) => handleOptionChange(key, e.target.value)}
            />
            {/* Add radio button for each option */}
            <input
              type="radio"
              name={`correctOption${id}`}
              value={key}
              checked={correctOption === key}
              onChange={() => handleCorrectOptionChange(key)}
              className="mt-2"
            />
            <label className="ml-1">Correct Option</label>
          </div>
        ))}
      </div>

      <Button
        onClick={handleDeleteQuestion}
        customClass="ml-2 bg-red-500 text-white translate mt-3"
      >
        Delete Question
      </Button>
    </div>
  );
}

export default Question;
