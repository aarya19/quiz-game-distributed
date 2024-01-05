// App.js
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import EnterRoom from "./EnterRoom";
import LandingPage from "./LandingPage";
import Navbar from "./NavBar";
import QuizMasterLandingPage from "./QuizMasterLandingPage";
import AddQuiz from "./AddQuiz";
import QuestionPanelPage from "./QuestionPanelPage";
import CreateRoom from "./CreateRoom";

function App() {
  return (
    <Router>
      <div className="">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route
            path="/:username/quizmaster"
            element={<QuizMasterLandingPage />}
          />
          <Route path="/:username/addquiz" element={<AddQuiz />} />
          <Route
            path="/gameRoom/:playerId/:roomId"
            element={<QuestionPanelPage />}
          />

          <Route path="/:username/startquiz/:quizID" element={<CreateRoom />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
