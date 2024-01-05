import React, { useState, useEffect, useRef } from "react";
import { useParams, useHistory, useNavigate } from "react-router-dom";
import Navbar from "./NavBar";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import Button from "./Button";

const CreateRoom = () => {
  const [players, setPlayers] = useState([]);
  const [numPlayers, setNumPlayers] = useState(players.length);
  const [quizStarted, setQuizStarted] = useState(false);
  const [scoresReceived, setScoresReceived] = useState(false);
  const { quizID } = useParams();
  const navigate = useNavigate();
  const ref = useRef();

  // Define 'columns'
  const columns = [
    { key: "playerName", label: "Player Name" },
    { key: "score", label: "Score" },
  ];

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/game-socket");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe("/events/joinedPlayers", (response) => {
        if (response.body) {
          updatePlayerList(response.body);
        }
      });

      stompClient.subscribe("/events/scores", (response) => {
        if (response.body) {
          updateScores(response.body);
          setScoresReceived(true);
        }
      });
    });
  }, []);

  const updatePlayerList = (newPlayer) => {
    const parsedNewPlayer = JSON.parse(newPlayer);
    console.log(parsedNewPlayer);
    console.log(parsedNewPlayer.roomId);

    if (parsedNewPlayer.roomId === quizID) {
      console.log("Quiz ID:", parsedNewPlayer.quizID);
      setPlayers((prevPlayers) => {
        const existingPlayerIndex = prevPlayers.findIndex(
          (player) => player.playerName === parsedNewPlayer.playerName
        );

        if (existingPlayerIndex !== -1) {
          if (
            prevPlayers[existingPlayerIndex].quizID === parsedNewPlayer.roomId
          ) {
            prevPlayers[existingPlayerIndex] = {
              playerName: parsedNewPlayer.playerName,
              score: parsedNewPlayer.score,
              quizID: parsedNewPlayer.quizID,
            };
          }
        } else {
          prevPlayers.push({
            playerName: parsedNewPlayer.playerName,
            score: parsedNewPlayer.score,
            quizID: parsedNewPlayer.quizID,
          });
        }

        setNumPlayers(prevPlayers.length);
        return [...prevPlayers];
      });
    }
  };

  const updateScores = (responseBody) => {
    const updatedScores = JSON.parse(responseBody);
    updatedScores.sort((a, b) => b.score - a.score);
    setPlayers(updatedScores);
  };

  const onStartQuiz = () => {
    fetch("http://localhost:8080/app/startgame?quizId=" + quizID, {
      method: "POST",
    })
      .then((response) => response.text())
      .then((data) => {
        alert(data);
        setQuizStarted(true);
      })
      .catch((error) => {
        console.error("Error starting the quiz:", error);
      });
  };

  const goBack = () => {
    navigate(-1);
  };

  return (
    <div className="text-center">
      <Navbar />

      <div>
        <h1 className="text-5xl font-handwriting mb-4">
          Number of Players Joined: {numPlayers}
        </h1>

        <table
          ref={ref}
          className="border-collapse border border-gray-800 mx-auto mb-8"
        >
          <thead>
            <tr>
              {columns.map((col) => (
                <th
                  key={col.key}
                  className="border border-gray-800 p-2 font-bold"
                >
                  {col.label}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {players.map((player) => (
              <tr key={player.playerName}>
                {columns.map((col) => (
                  <td key={col.key} className="border border-gray-800 p-2">
                    {player[col.key]}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>

        {!quizStarted && (
          <Button
            onClick={onStartQuiz}
            customClass="mt-2 bg-orange hover:bg-b2"
          >
            Start Quiz
          </Button>
        )}

        {scoresReceived && quizStarted && (
          <Button onClick={goBack} customClass="mt-2 bg-red hover:bg-b2">
            Go Back
          </Button>
        )}
      </div>
    </div>
  );
};

export default CreateRoom;
