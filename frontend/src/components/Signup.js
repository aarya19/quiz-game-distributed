import React, { useState } from "react";
import Button from "./Button";
import TextField from "./TextField";
import { useNavigate } from "react-router-dom";

const Signup = ({ onLoginClick }) => {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState(""); // Changed from userName to email
  const navigate = useNavigate();

  const handleSignup = async () => {
    try {
      const response = await fetch("http://localhost:8080/app/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name,
          password,
          userName: email, // Use email as userName (assuming email is the username in your backend)
        }),
        credentials: "include",
      });

      if (response.ok) {
        const responseBody = await response.text();
        console.log("Response Body:", responseBody);

        // Display a success message or handle the response as needed
        alert("Signup successful! ");
        navigate(`/quizmaster/${name}`);
      } else {
        // Handle non-200 status codes
        console.error("Signup failed with status:", response.status);
        alert("Signup failed. Please check your details and try again.");
      }
    } catch (error) {
      console.error("Error during signup:", error.message);
      alert(
        "An unexpected error occurred during signup. Please try again later."
      );
    }
  };

  return (
    <div className="flex items-center justify-center h-screen w-screen">
      <div className="flex flex-col items-center justify-center rounded-lg border-b2 p-10 border-4 h-1/2 w-full">
        <h1 className="text-4xl font-handwriting mb-3 text-b1 text-center">
          Signup
        </h1>
        <TextField
          label="Name"
          type="text"
          placeholder="Enter your name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <TextField
          label="Password"
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <TextField
          label="Email"
          type="email"
          placeholder="Enter your email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <Button onClick={handleSignup} customClass="">
          Signup
        </Button>
        <Button onClick={onLoginClick} customClass="mt-2 bg-orange hover:bg-b2">
          Login
        </Button>
      </div>
    </div>
  );
};

export default Signup;
