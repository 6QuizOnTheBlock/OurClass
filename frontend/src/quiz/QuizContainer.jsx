import React, { useEffect, useState } from 'react';
import { useWebSocket } from '../utils/WebSocketProvider';
import { useParams } from 'react-router-dom';
import useUserStore from '../stores/member';
import { useNavigate } from "react-router-dom";
import SubjectiveQuiz from './SubjectiveQuiz';
import Answer from './Answer';
import RankingScreen from './Ranking';
import Layout from '../components/Layout';
import { toast, useToast } from '../components/ui/use-toast';
import { Toaster } from '../components/ui/toaster';

const QuizContainer = () => {
  const [questions, setQuestions] = useState([]); 
  const [answer, setAnswer] = useState({});
  const [currentQuestion, setCurrentQuestion] = useState(0); 
  const [currentScreen, setCurrentScreen] = useState('Subject');
  const navigate = useNavigate();
  const {Qid} = useParams();
  const {stompClient} = useWebSocket();
  const accessToken = useUserStore(state => state.accessToken);
  const userInfo = useUserStore(state => state.userInfo);
  const subUrl = `/subscribe/gamer/${Qid}`;
  const pubUrl = `/publish/gamer`;

  useEffect(() => {
    console.log("StompClient 준비상태:", stompClient)
    if(stompClient){
      const payload = {
        id: userInfo.id,
        quizGameId:  Qid,
        name: userInfo.name,
        photo: userInfo.photo,
        point: 0,
        gamerStatus: 'ENTER'
      };
      // 대기열 입성
      stompClient.subscribe(subUrl, onGamerReceived, {Authorization: accessToken, type: "quiz"});
      stompClient.send(pubUrl, {Authorization: accessToken, type: "quiz"}, JSON.stringify(payload));
      // quiz 받기
      const quiz ={
        quizGameId: Qid,
        id:1
      }
      stompClient.subscribe(`/subscribe/question/${Qid}`, onQuestionReceived,{Authorization: accessToken, type: "quiz"})
      stompClient.send(`/publish/question`,{Authorization: accessToken, type: "quiz"}, JSON.stringify(quiz));
      // 사람들이 보낸 답변 받기 
      stompClient.subscribe(`/subscribe/answer/${Qid}`, onAnswerReceived,{Authorization: accessToken, type: "quiz"})
    
    }

  }, [])
    // 메세지 세팅
    const onQuestionReceived = (payload) => {
        var receivedData = JSON.parse(payload.body);
        setQuestions(receivedData);
        console.log(receivedData);
    }
    // 게이머 세팅
    function onGamerReceived (payload) {
        var gamer = JSON.parse(payload.body);
        console.log("들어온 게이머:", gamer);
        
    }
    // 답변 세팅 
    function onAnswerReceived (payload) {
       var answer = JSON.parse(payload.body);
       console.log(answer);
       setAnswer(()=>answer);
       toast({
        title: `${answer.name}님 답변은`,
        description: `${answer.submit == answer.ans? "맞았습니다." : "틀렸습니다."}`
       })
    }

    const nextQuestion = () => {
      setCurrentQuestion((previndex) => {
        if(previndex >= questions.length-1){
          navigate("/quizEnd");
          return 0;
        }
        return previndex +1;
      })
    }

  // 화면 렌더링 
  const renderScreen = () => {
    if (!questions.length) return <Layout>Loading questions...</Layout>; // 데이터를 기다리는 동안 로딩 표시
    switch (currentScreen) {
      case 'Subject':
        return (<>
        <SubjectiveQuiz setCurrentScreen={setCurrentScreen} Qid={Qid} question={questions[currentQuestion]} />;
                <Toaster/>
        </>)
      case 'Answer':
        return <Answer setCurrentScreen={setCurrentScreen} Qid={Qid} answer={answer} point={questions[currentQuestion].point} />;
      case 'Ranking':
        return <RankingScreen currentScreen={currentScreen} setCurrentScreen={setCurrentScreen} nextQuestion={nextQuestion} Qid={Qid}  />;
      default:
        return <RankingScreen  currentScreen={currentScreen} setCurrentScreen={setCurrentScreen} nextQuestion={nextQuestion} Qid={Qid} />;
    }
  };

  return (
    <>
      {renderScreen()}
    </>
  );
};

export default QuizContainer;

