import Layout from "../components/Layout"
import React, { useEffect, useState } from 'react';
import { Button } from "../components/ui/button";
import { Input } from "../components/ui/input";
import { useWebSocket } from '../utils/WebSocketProvider';
import useUserStore from '../stores/member';
import CountdownTimer from "../components/CountdownTime";
import QuestionPart from "./QuestionPart";

const SubjectiveQuiz = ({setCurrentScreen, Qid, question}) => {
    const {stompClient} = useWebSocket();
    const accessToken = useUserStore(state => state.accessToken);
    const userInfo = useUserStore(state => state.userInfo);
    const [inputValue, setInputValue] = useState(''); // 문제에 대한 답변 제출
    // 타이머 종료 시 호출될 함수
    const handleTimerEnd = () => {
        setCurrentScreen('Answer');
    };


    const handleInputChange = (e) => {
        setInputValue(e.target.value); // 입력값 변경 처리

      };
    
      const handleSubmit = () => {
        console.log(`입력한 내용: ${inputValue}`);
        const payload = {
            quizGameId: Qid,
            quizId: question.id,
            answer: inputValue
        }

        stompClient.send(`/publish/answer`,{Authorization: accessToken, type: "quiz"}, JSON.stringify(payload));
      };

    return(
    <Layout>
        {/* 문제 번호 */}
        <div className=" text-white text-center my-2">Question</div>
        {/* 시간 제한 */}
        <CountdownTimer onTimerEnd={handleTimerEnd} time={10}/>
        {/* 문제 내용 적는 곳 */}
        <QuestionPart question={question.question} />
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