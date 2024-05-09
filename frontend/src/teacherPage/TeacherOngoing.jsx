import React, { useEffect, useState } from 'react';
import { Button } from '../components/ui/button';
import Layout from '../components/Layout';



const TeacherOngoing = () => {
// 버튼 색상 상태를 관리하는 state
  const [isActive, setIsActive] = useState(false);

  // 버튼 클릭 이벤트 핸들러
  const toggleButtonColor = () => {
    setIsActive(!isActive);
  };

    return(
      <Layout>
            <img
              src="https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/4dcbb2f3-016a-4750-ab38-dc6113892065_teacher_man.png"
              alt="Teacher"
              className="w-32 h-32 rounded-full mx-auto border-2"
            />
            <div className="mt-4">
              <h1 className="text-lg font-bold">홍유준 선생님</h1>
              <p className="text-sm text-gray-600">구미 초등학교 3학년 1반</p>
            </div>
            <div className="flex flex-col justify-between space-y-2  mt-4">
              <Button className={` text-white py-6 px-8 rounded-full ${isActive? 'bg-blue-500':'bg-[#152B65]' }`}>힌트 보여주기</Button>
              <Button className={` text-white py-6 px-8 rounded-full ${isActive? 'bg-blue-500':'bg-[#152B65]' }`}>다음 문제 진행!</Button>
            </div>
      </Layout>
    )
}


export default TeacherOngoing