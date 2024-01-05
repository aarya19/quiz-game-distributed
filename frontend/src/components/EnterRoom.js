import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";
import { useNavigate } from "react-router-dom";
const EnterRoom = () => {
  const [roomId, setRoomId] = useState("");
  const [playerId, setPlayerId] = useState("");
  const [playerName, setPlayerName] = useState("");

  const navigate = useNavigate();

  const handleEnterRoom = async () => {
    console.log("Entering room with ID:", roomId);
    console.log("Entering room with id:", playerId);
    console.log("Entering room with name:", playerName);

    const playerData = {
      playerName: playerName,
      roomId: roomId,
      playerId: playerId,
    };

    try {
      const response = await fetch("http://localhost:8080/app/joinRoom", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(playerData),
        credentials: "include",
      });

      // Check if the status code is 200 (OK)
      if (response.status === 200) {
        const responseBody = await response.text();
        console.log("Response:", responseBody);

        //Navigate to Question Panel Page
        navigate(`/gameRoom/${playerId}/${roomId}`);
      } else {
        // Handle non-200 status codes
        console.error("Entering failed with status:", response.status);
        alert(
          "Entering the room has failed. Please check your credentials and try again."
        );
      }
    } catch (error) {
      console.error("Error during entering room:", error.message);
      alert(
        "An unexpected error occurred during entering the room. Please try again later."
      );
    }
  };

  return (
    <div className="flex items-center justify-center h-screen w-screen">
      <div className="flex flex-col items-center justify-center rounded-lg border-b2 p-10 border-4 h-1/2 w-full">
        <h1 className="text-4xl font-handwriting mb-8 text-b1 text-center">
          Enter Room
        </h1>
        <TextField
          label="Room ID"
          type="text"
          placeholder="Enter the room ID"
          value={roomId}
          onChange={(e1) => setRoomId(e1.target.value)}
        />
        <TextField
          label="Player ID"
          type="text"
          placeholder="Enter a unique player id"
          value={playerId}
          onChange={(e2) => setPlayerId(e2.target.value)}
        />
        <TextField
          label="Player Name"
          type="text"
          placeholder="Enter Your Name"
          value={playerName}
          onChange={(e3) => setPlayerName(e3.target.value)}
        />
        <Button onClick={handleEnterRoom} customClass="mt-3">
          Enter Room
        </Button>
      </div>
    </div>
  );
};

export default EnterRoom;
