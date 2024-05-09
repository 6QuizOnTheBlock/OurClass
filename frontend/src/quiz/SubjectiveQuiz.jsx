import Layout from "../components/Layout"
import React, { useState } from 'react';
import { Card, CardContent, CardTitle } from "../components/ui/card";
import { Button } from "../components/ui/button";
import { Input } from "../components/ui/input";
import CountdownTimer from "../components/CountdownTime";
import { useNavigate } from "react-router-dom";
import QuestionPart from "./QuestionPart";
import { useParams } from 'react-router-dom';

const SubjectiveQuiz = () => {
    const navigate = useNavigate();
    const {Qid} = useParams();

    // 타이머 종료 시 호출될 함수
    const handleTimerEnd = () => {
       navigate('/answer/'+ parseInt(Qid));
    };


    // 문제에 대한 답변 제출
    const [inputValue, setInputValue] = useState(''); // 입력값 상태 관리
    const handleInputChange = (e) => {
        setInputValue(e.target.value); // 입력값 변경 처리
      };
    
      const handleSubmit = () => {
        alert(`입력한 내용: ${inputValue}`); // 입력값 제출 처리, 실제 구현에서는 다른 로직을 추가할 수 있습니다.
      };

    return(
    <Layout>
        {/* 문제 번호 */}
        <div className=" text-white text-center my-2">Question {Qid}/5</div>
        {/* 시간 제한 */}
        <CountdownTimer onTimerEnd={handleTimerEnd}/>
        {/* 문제 내용 적는 곳 */}
        <QuestionPart img={"https://www.agoda.com/wp-content/uploads/2020/04/Jeju-Island-hotels-things-to-do-in-Jeju-Island-South-Korea.jpg"} question={"제주도는 삼다도라 불리는데, 여기서 삼다는 무엇일까?"} />
        {/* 답 입력 */}
            <div className="mt-4 w-full p-6 flex items-center">
                <Input
                    type="text"
                    value={inputValue}
                    onChange={handleInputChange}
                    className="flex-1 p-3 border-2 bg-slate-100 text-[#152B65] border-gray-300 rounded-l-full focus:outline-none focus:border-slate-500"
                    placeholder="여기에 입력하세요"
                    style={{ height: '2.5rem' }} // 스타일로 높이를 지정
                />
                <Button
                    onClick={handleSubmit}
                    className="bg-[#152B65] text-white px-4 py-3 rounded-r-full hover:bg-blue-600"
                    style={{ height: '2.5rem' }} // 스타일로 높이를 지정
                >
                    입력
                </Button>
            </div>

    </Layout>
    
    )

}

export default SubjectiveQuiz