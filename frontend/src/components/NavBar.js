import React from "react";
import Logo from "./Logo";

const Navbar = ({ currentPage }) => {
  if (currentPage === "game-page") {
    return (
      <nav className="bg-b1 p-2">
        <div className="container mx-auto flex items-center justify-center">
          <Logo className="text-orange hover:text-w" />
        </div>
      </nav>
    );
  }

  return (
    <nav className="bg-b1 p-2">
      <div className="container mx-auto flex items-center justify-between">
        <Logo className="text-orange hover:text-w" />

        <div className="flex space-x-4">
          <a
            href="/quizzes"
            className={`text-white  hover:bg-orange p-3 rounded-lg ${
              currentPage === "quizzes" && "font-bold"
            }`}
          >
            Quizzes
          </a>
          <a
            href="/my-profile"
            className={`text-white hover:bg-orange p-3 rounded-lg ${
              currentPage === "my-profile" && "font-bold"
            }`}
          >
            My Profile
          </a>

          <a href="/" className="text-white  hover:bg-orange p-3 rounded-lg">
            Sign Out
          </a>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
