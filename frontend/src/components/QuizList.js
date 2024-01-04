// QuizList.js
import React from "react";

const QuizList = ({ quizzes }) => {
  return (
    <ul>
      {quizzes.map((quiz) => (
        <li
          key={quiz.id}
          className="my-4 border p-4 flex items-center justify-between"
        >
          <div>
            <h1 className="text-xl font-bold mb-2">Room ID: {quiz.roomId}</h1>
            <p>Title: {quiz.title}</p>
          </div>
          <div className="flex space-x-2">
            <button className="bg-blue-500 text-white px-2 py-1 hover:bg-blue-700 rounded-lg">
              Edit
            </button>
            <button className="bg-red-500 text-white px-2 py-1 hover:bg-red-700 rounded-lg">
              Delete
            </button>
            <button className="bg-green-500 text-white px-2 py-1 hover:bg-green-700 rounded-lg">
              Start Quiz
            </button>
          </div>
        </li>
      ))}
    </ul>
  );
};

export default QuizList;
