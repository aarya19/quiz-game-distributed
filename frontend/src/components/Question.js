// Question.js
import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";

function Question({ id, questionNumber, onDelete }) {
  const [question, setQuestion] = useState("");
  const [options, setOptions] = useState(["", "", "", ""]);
  const [correctOption, setCorrectOption] = useState(""); // Track the correct option

  const handleAddOption = () => {
    if (options.length < 4) {
      setOptions([...options, ""]);
    }
  };

  const handleOptionChange = (index, value) => {
    const newOptions = [...options];
    newOptions[index] = value;
    setOptions(newOptions);
  };

  const handleDeleteOption = (index) => {
    const newOptions = [...options];
    newOptions.splice(index, 1);
    setOptions(newOptions);
  };

  const handleDeleteQuestion = () => {
    onDelete(id);
  };

  const handleCorrectOptionChange = (event) => {
    setCorrectOption(event.target.value);
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
        {options.map((option, index) => (
          <div key={index} className="flex flex-col items-center">
            <TextField
              label={`Option ${index + 1}`}
              type="text"
              placeholder={`Enter option ${index + 1}`}
              value={option}
              onChange={(e) => handleOptionChange(index, e.target.value)}
            />
            {options.length > 2 && (
              <Button
                onClick={() => handleDeleteOption(index)}
                customClass="text-red-500 mt-2"
              >
                Delete
              </Button>
            )}
            {/* Add radio button for each option */}
            <input
              type="radio"
              name={`correctOption${id}`}
              value={index}
              checked={correctOption === `${index}`}
              onChange={handleCorrectOptionChange}
              className="mt-2"
            />
            <label className="ml-1">Correct Option</label>
          </div>
        ))}
      </div>

      {options.length < 4 && (
        <Button
          onClick={handleAddOption}
          customClass="mt-5 bg-blue-500 text-white"
        >
          Add Option
        </Button>
      )}
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
