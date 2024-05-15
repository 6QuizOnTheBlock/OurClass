
import React, { useEffect, useState } from 'react';
import { CarouselPlugin } from './WaitingMemberCarousel';
import Layout from '../components/Layout';
import { useWebSocket } from '../utils/WebSocketProvider';
import { useParams } from 'react-router-dom';
import useUserStore from '../stores/member';
import CountdownTimer from '../components/CountdownTime';



const WaitingRoomPage = () => {
  const {Qid} = useParams();
  const {stompClient} = useWebSocket();
  const accessToken = useUserStore(state => state.accessToken);
  const userInfo = useUserStore(state => state.userInfo);
  const subUrl = `/subscribe/gamer/${Qid}`;
  const pubUrl = `/publish/gamer`;
  const [countDown, setCountDown] = useState(60);

  // 타이머 종료 시 호출될 함수
  const handleTimerEnd = () => {
    // navigate('/answer/'+ parseInt(Qid));
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
      stompClient.subscribe(subUrl, onGamerReceived, {Authorization: accessToken, type: "quiz"});
      stompClient.send(pubUrl, {Authorization: accessToken, type: "quiz"}, JSON.stringify(payload));
      // 카운트다운 시간 받기 
      stompClient.subscribe('/queue/time',(payload)=>{console.log(payload)},{Authorization: accessToken, type: "quiz"})
      stompClient.send("/publish/current",{Authorization: accessToken, type: "quiz"},JSON.stringify({'request': 'current_time'}))
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
    
  }, [stompClient])

  // 게이머 세팅
  function onGamerReceived (payload) {
    var gamer = JSON.parse(payload.body);
    console.log("들어온 게이머:", gamer);

    setWaitingList(gamer);
  }
  // countdown 세팅 
  const receivedCount = (response) => {
    let remainingTime = JSON.parse(response.body);
    console.log(remainingTime);
    setCountDown(remainingTime);
  }

  const [waitingList, setWaitingList] = React.useState([])

  



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

