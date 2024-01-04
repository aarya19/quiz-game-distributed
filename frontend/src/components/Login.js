import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";
import { useNavigate } from "react-router-dom";

const Login = ({ onSignupClick }) => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/app/signin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userName,
          password,
        }),
        credentials: "include",
      });

      // Check if the status code is 200 (OK)
      if (response.status === 200) {
        const responseBody = await response.text();

        try {
          const parsedResponse = JSON.parse(responseBody);
          console.log("Parsed Response:", parsedResponse);

          if (parsedResponse.statusInfo === "OK") {
            // Successful login
            console.log("Login successful!");
            navigate(`/quizmaster/${userName}`);
          } else {
            console.error("Login failed:", parsedResponse.message);
            alert("Login failed. Please check your credentials and try again.");
          }
        } catch (error) {
          console.error("Error parsing response:", error.message);
          alert(
            "An unexpected error occurred during login. Please try again later."
          );
        }
      } else {
        // Handle non-200 status codes
        console.error("Login failed with status:", response.status);
        alert("Login failed. Please check your credentials and try again.");
      }
    } catch (error) {
      console.error("Error during login:", error.message);
      alert(
        "An unexpected error occurred during login. Please try again later."
      );
    }
  };

  return (
    <div className="flex items-center justify-center h-screen w-screen">
      <div className="flex flex-col items-center justify-center rounded-lg border-b2 p-10 border-4 h-1/2 w-full">
        <h1 className="text-4xl font-handwriting mt-8 text-b1 text-center">
          Login to Create Quiz
        </h1>
        <TextField
          label="Username"
          type="text"
          placeholder="Enter your username"
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
        />
        <TextField
          label="Password"
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button onClick={handleLogin} customClass="mt-3">
          Login
        </Button>
        <Button
          onClick={onSignupClick}
          customClass="mt-3 bg-orange hover:bg-b2"
        >
          Sign Up
        </Button>
      </div>
    </div>
  );
};

export default Login;
