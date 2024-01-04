import React, { useState } from "react";
import Login from "./Login";
import Signup from "./Signup";
import EnterRoom from "./EnterRoom";
import Logo from "./Logo";

const LandingPage = () => {
  const [showLogin, setShowLogin] = useState(true);

  const handleSignupClick = () => {
    setShowLogin(false);
  };

  const handleLoginClick = () => {
    setShowLogin(true);
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen w-screen border-30 border-b1 p-24">
      <Logo className="text-orange hover:text-b2 translate-y-32" />

      <div className="flex flex-col md:flex-row mx-auto max-w-screen-md md:items-center">
        {showLogin ? (
          <Login onSignupClick={handleSignupClick} />
        ) : (
          <Signup onLoginClick={handleLoginClick} />
        )}
        <div className="md:border-r-5 md:border-b-2 md:mr-4 hidden md:block"></div>
        <EnterRoom />
      </div>
    </div>
  );
};

export default LandingPage;
