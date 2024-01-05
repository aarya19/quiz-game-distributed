import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";

function Room() {
  const [name, setName] = useState("");
  const [roomID, setRoomID] = useState("");

  const handleLogin = () => {
    // Handle login logic here
    console.log("Entering in with:", name, roomID);
  };

  return (
    <div className="flex items-center justify-center h-screen w-screen">
      <div className="w-1/4 text-center rounded-lg border-b-2 p-10 border-4 max-h-full">
        <h1 className="text-4xl font-handwriting mb-8 text-b1 text-center">
          Enter the Room
        </h1>
        <TextField
          label="Name"
          type="text"
          placeholder="Enter your name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <TextField
          label="Room ID"
          type="text"
          placeholder="Enter the room ID"
          value={roomID}
          onChange={(e) => setRoomID(e.target.value)}
        />
        <Button onClick={handleLogin} customClass="mt-3 ">
          Enter
        </Button>
      </div>
    </div>
  );
}

export default Room;
