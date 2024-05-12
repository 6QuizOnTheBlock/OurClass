import React, { useState } from 'react';
import { Button } from "./components/ui/button";
import { Calendar } from "./components/ui/calendar";
import "./index.css";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import WaitingRoomPage from './page/WaitingRoomPage';
import TeacherHome from './teacherPage/TeacherHome';
import TeacherOngoing from './teacherPage/TeacherOngoing';
import SubjectiveQuiz from './quiz/SubjectiveQuiz';
import QuizComponent from './quiz/MulitpleQuiz';
import Answer from './quiz/Answer';
import QuizEnd from './quiz/QuizEnd';
import RankingScreen from './quiz/Ranking';
import LoginForm from './page/LoginFrom';


function App() {

  return (
    <BrowserRouter>
       <Routes>
        <Route path='/:quizGameId/:uuid' element={<LoginForm/>}></Route>
        <Route path='/home' element={<TeacherHome/>}></Route>
         <Route path='/teacherRemote' element={<TeacherOngoing/>} ></Route>
         <Route path="/waitingRoom" element={<WaitingRoomPage/>}></Route>
         <Route path='/subject/:Qid' element={<SubjectiveQuiz/>}></Route>
         <Route path='/multiple/:Qid' element={<QuizComponent/>}></Route>
         <Route path='/answer/:Qid' element={<Answer/>}></Route>
         <Route path='/quizEnd' element={<QuizEnd/>}></Route>
         <Route path='/ranking' element={<RankingScreen/>}></Route>
       </Routes>
    </BrowserRouter>
  )
}

export default App
