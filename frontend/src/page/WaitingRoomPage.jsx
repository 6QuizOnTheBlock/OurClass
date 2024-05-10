
import React, { useEffect, useState } from 'react';
import { CarouselPlugin } from './WaitingMemberCarousel';
import Layout from '../components/Layout';

const WaitingRoomPage = () => {


  const [waitingList, setWaitingList] = React.useState([
    {
      studentId: 1,
      quizGameId: 1,
      profileImg: "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/0c37e053-eeee-4e69-b703-ebf7ee70fb1c_student_boy.png",
      name: "오하빈"
    },
    {
      studentId: 2,
      quizGameId: 1,
      profileImg: "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/6d664e70-cb66-49f0-874d-938ae9e4c7f6_student_girl.png",
      name: "김규리"
    },
    {
      studentId: 3,
      quizGameId: 1,
      profileImg: "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/0c37e053-eeee-4e69-b703-ebf7ee70fb1c_student_boy.png",
      name: "오하빈"
    },
    {
      studentId: 4,
      quizGameId: 1,
      profileImg: "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/6d664e70-cb66-49f0-874d-938ae9e4c7f6_student_girl.png",
      name: "김규리"
    },
    {
      studentId: 5,
      quizGameId: 1,
      profileImg: "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/0c37e053-eeee-4e69-b703-ebf7ee70fb1c_student_boy.png",
      name: "오하빈"
    },
    {
      studentId: 6,
      quizGameId: 1,
      profileImg: "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/6d664e70-cb66-49f0-874d-938ae9e4c7f6_student_girl.png",
      name: "김규리"
    }
  ])



    return(
      <Layout>
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

