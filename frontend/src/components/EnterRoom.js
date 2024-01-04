import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";

const EnterRoom = () => {
  const [roomId, setRoomId] = useState("");

  const handleEnterRoom = () => {
    console.log("Entering room with ID:", roomId);
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
          onChange={(e) => setRoomId(e.target.value)}
        />
        <Button onClick={handleEnterRoom} customClass="mt-3">
          Enter Room
        </Button>
      </div>
    </div>
  );
};

export default EnterRoom;
