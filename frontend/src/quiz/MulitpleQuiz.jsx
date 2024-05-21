import React, { useState } from 'react';
import Layout from '../components/Layout';
import CountdownTimer from '../components/CountdownTime';
import { useNavigate } from "react-router-dom";
import QuestionPart from './QuestionPart';
import { useParams } from 'react-router-dom';

function QuizComponent() {
  const navigate = useNavigate();
  const {Qid} = useParams();
  
  const handleTimerEnd = () => {
   navigate('/answer/'+ parseInt(Qid))
    };
  
  // 사용자 답변 관리-------------------------------------------------------------------
  const [selected, setSelected] = useState(null); // 사용자가 선택한 답변을 관리합니다.

  // 질문과 답변 데이터, 실제 구현에서는 이 데이터를 API 등에서 가져올 수 있습니다.
  const question = "How many students in your class _from Korea?";
  const answers = ["come", "comes", "are coming", "does come"];

  // 답변 선택 핸들러
  const handleAnswerSelect = (answer) => {
    setSelected(answer);
    console.log(selected);
  };

  return (
    <Layout>
      {/* 문제 번호 */}
      <div className=" text-white text-center my-2">Question {Qid}/5</div>
      {/* 진행 바 및 타이머 */}
      <CountdownTimer onTimerEnd={handleTimerEnd}/>
      {/* 질문 */}
      <QuestionPart img={""} question={"how many students in your class ____ from korea?"}/>

      {/* 답변 버튼들 */}
      <div className="space-y-2 mt-4">
        {answers.map((answer, index) => (
          <button
            key={index}
            onClick={() => handleAnswerSelect(answer)}
            className={`w-full p-3 rounded-full text-center text-white ${selected === answer ? 'bg-slate-500' : 'bg-[#152B65]'}`}
          >
            {answer}
          </button>
        ))}
      </div>
    </Layout>
  );
}

export default QuizComponent;