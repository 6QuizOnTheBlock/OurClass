
import React, { useEffect, useState } from 'react';
import { CarouselPlugin } from './WaitingMemberCarousel';
import Layout from '../components/Layout';
import { useWebSocket } from '../utils/WebSocketProvider';
import { useParams } from 'react-router-dom';
import useUserStore from '../stores/member';
import CountdownTimer from '../components/CountdownTime';
import { useNavigate } from "react-router-dom";


const WaitingRoomPage = () => {
  const navigate = useNavigate();
  const {Qid} = useParams();
  const {stompClient} = useWebSocket();
  const accessToken = useUserStore(state => state.accessToken);
  const userInfo = useUserStore(state => state.userInfo);
  const subUrl = `/subscribe/gamer/${Qid}`;
  const pubUrl = `/publish/gamer`;
  const [countDown, setCountDown] = useState(10);
  const [waitingList, setWaitingList] = useState([])


  // 타이머 종료 시 호출될 함수
  const handleTimerEnd = () => {
    navigate('/quiz/'+ parseInt(Qid));
  };

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
      stompClient.subscribe(subUrl,onGamerReceived, {Authorization: accessToken, type: "quiz"});
      stompClient.send(pubUrl, {Authorization: accessToken, type: "quiz"}, JSON.stringify(payload));
    }
  return () => {
     const leave = {
      id: userInfo.id,
      quizGameId:  Qid,
      name: userInfo.name,
      photo: userInfo.photo,
      point: 0,
      gamerStatus: 'LEAVE'
    };

    if(stompClient){
      stompClient.send(pubUrl,  {Authorization: accessToken, type: "quiz"},JSON.stringify(leave))
    }
  }
    
  }, [stompClient, Qid, accessToken])

  useEffect(() => {
    console.log("waitingList has been updated:", waitingList);
  }, [waitingList]); // waitingList 변화 감지
  

  // 게이머 세팅
  function onGamerReceived (payload) {
    var gamer = JSON.parse(payload.body);
    console.log("들어온 게이머:", gamer);
    setWaitingList(()=>[...gamer]);
  }


  return(
    <Layout>
      <CountdownTimer time={countDown} onTimerEnd={handleTimerEnd}/>
        <img
          src="https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/4dcbb2f3-016a-4750-ab38-dc6113892065_teacher_man.png"
          alt="Profile"z
          className="w-32 h-32  mx-auto"
        />
        <h1 className="text-gray-600 font-monoplexKRNerd-Bold mt-3">홍유준 선생님의 퀴즈방</h1>
        <p className="text-gray-600 font-monoplexKRNerd mt-1">지금은 기다리는 중...{waitingList.length}/{waitingList.length}</p>
        <div className="flex space-x-4 justify-center mt-5">
        </div>
        <CarouselPlugin waitingList={waitingList}/>
    </Layout>
  )

}

export default WaitingRoomPage;

