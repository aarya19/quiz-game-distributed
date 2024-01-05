import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import CurrentQuestion from "./CurrentQuestion";
import Navbar from "./NavBar";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const sendScore = async (answer) => {
  try {
    console.log("sending for score with answer", answer.answer);
    const response = await fetch("http://localhost:8080/app/addScore", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(answer),
      credentials: "include",
    });

    if (response.status === 200) {
      const responseBody = await response.text();
      console.log("Response:", responseBody);
    } else {
      console.error("Score update failed with status:", response.status);
      alert("Score update has failed.");
    }
  } catch (error) {
    console.error("Error during updating score:", error.message);
    alert("An unexpected error occurred during updating the score!.");
  }
};

const QuestionPanelPage = () => {
  let { playerId } = useParams();
  let { roomId } = useParams();
  const navigate = useNavigate(); // Import useNavigate

  const [answerToSend, setAnswerToSend] = useState({
    playerId: `${playerId}`,
    quizId: `${roomId}`,
    questionId: null,
    question: null,
    answer: null,
  });

  const [quizEnded, setQuizEnded] = useState(false);
  const [players, setPlayers] = useState([]);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/game-socket");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe("/events/question", (response) => {
        console.log("Got a question that looks like this! ");
        const newQuestion = JSON.parse(response.body);

        setAnswerToSend((previousAnswer) => {
          const updatedAnswer = {
            ...previousAnswer,
            question: newQuestion,
          };

          return updatedAnswer;
        });
      });

      stompClient.subscribe("/events/scores", (response) => {
        if (response.body) {
          const updatedScores = JSON.parse(response.body);
          updatedScores.sort((a, b) => b.score - a.score);
          setPlayers(updatedScores);
          setQuizEnded(true);
        }
      });
    });
  }, []);

  const sendSelectedOption = (selectedOption) => {
    console.log("option saved");
    setAnswerToSend((previousAnswer) => {
      const updatedAnswer = {
        ...previousAnswer,
        answer: selectedOption,
      };

      sendScore(updatedAnswer);

      return updatedAnswer;
    });
  };

  const goBack = () => {
    navigate(-1);
  };

  return (
    <div>
      <Navbar currentPage="game-page" />
      <div className="container mx-auto mt-20 flex items-center justify-center">
        {quizEnded ? (
          <div>
            <h1 className="text-5xl font-handwriting text-center mb-5">
              Quiz Ended
            </h1>
            <table className="border-collapse border border-gray-800 mx-auto mb-8 text-xl">
              <thead>
                <tr>
                  <th className="border border-gray-800 p-2 font-bold">
                    Player Name
                  </th>
                  <th className="border border-gray-800 p-2 font-bold">
                    Score
                  </th>
                </tr>
              </thead>
              <tbody>
                {players.map((player) => (
                  <tr key={player.playerName}>
                    <td className="border border-gray-800 p-2">
                      {player.playerName}
                    </td>
                    <td className="border border-gray-800 p-2">
                      {player.score}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <button onClick={goBack}>Go Back</button>
          </div>
        ) : (
          <CurrentQuestion
            question={answerToSend.question}
            sendOption={sendSelectedOption}
          />
        )}
      </div>
    </div>
  );
};

export default QuestionPanelPage;
