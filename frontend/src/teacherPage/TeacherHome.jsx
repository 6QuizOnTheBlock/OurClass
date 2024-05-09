import React, { useEffect, useState } from 'react';
import { Button } from '../components/ui/button';
import Layout from '../components/Layout';


const TeacherHome = () => {

    return(
      <Layout>
        <img
          src="https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/4dcbb2f3-016a-4750-ab38-dc6113892065_teacher_man.png"
          alt="Teacher"
          className="w-32 h-32 rounded-full mx-auto"
        />
        <div className="mt-4">
          <h1 className="text-lg font-bold">홍유준 선생님</h1>
          <p className="text-sm text-gray-600">구미 초등학교 3학년 1반</p>
        </div>
        <div className="flex flex-col space-y-2 mt-4">
          <Button className="bg-[#152B65] text-white py-2 rounded-full">시작하기</Button>
        </div> 
      </Layout>
    )
}


export default TeacherHome;