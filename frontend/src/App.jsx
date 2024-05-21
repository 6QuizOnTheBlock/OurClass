import React, { useState } from 'react';
import { Button } from "./components/ui/button";
import { Calendar } from "./components/ui/calendar";
import "./index.css";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import WaitingRoomPage from './page/WaitingRoomPage';
import TeacherHome from './teacherPage/TeacherHome';
import TeacherOngoing from './teacherPage/TeacherOngoing';
import QuizEnd from './quiz/QuizEnd';
import RankingScreen from './quiz/Ranking';
import LoginForm from './page/LoginFrom';
import { WebSocketProvider } from './utils/WebSocketProvider';
import QuizContainer from './quiz/QuizContainer';


function App() {

  return (
    <BrowserRouter>
      <WebSocketProvider>
        <Routes>
          <Route path='/:quizGameId/:uuid' element={<LoginForm/>}></Route>
          <Route path='/home' element={<TeacherHome/>}></Route>
          <Route path='/teacherRemote' element={<TeacherOngoing/>} ></Route>
          <Route path="/waitingRoom/:Qid" element={<WaitingRoomPage/>}></Route>
          <Route path='/quiz/:Qid' element={<QuizContainer/>}></Route>
          <Route path='/quizEnd' element={<QuizEnd/>}></Route>
          <Route path='/ranking' element={<RankingScreen/>}></Route>
        </Routes>
      </WebSocketProvider>
    </BrowserRouter>
  )
}

export default App
