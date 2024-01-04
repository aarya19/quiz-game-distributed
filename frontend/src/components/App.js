// App.js
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import EnterRoom from "./EnterRoom";
import LandingPage from "./LandingPage";
import Navbar from "./NavBar";
import QuizMasterLandingPage from "./QuizMasterLandingPage";
import AddQuiz from "./AddQuiz";
import QuestionPanelPage from "./QuestionPanelPage";

function App() {
  return (
    <Router>
      <div className="">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route
            path="/quizmaster/:username"
            element={<QuizMasterLandingPage />}
          />
          <Route path="/addquiz" element={<AddQuiz />} />
          {/* Add routes for other components */}
          {/* <Route path="/addquiz" element={<AddQuiz />} />
              <Route path="/questionpanel" element={<QuestionPanelPage />} />
          */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
