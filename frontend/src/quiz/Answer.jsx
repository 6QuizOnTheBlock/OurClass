import CountdownTimer from "../components/CountdownTime";
import LayoutforAns from "../components/LayoutforAns";
import { Button } from "../components/ui/button";
import { useNavigate } from "react-router-dom";
import { useParams } from 'react-router-dom';
const Answer = ({setCurrentScreen, Qid, answer, point}) => {

    const handleTimerEnd = () => {
        setCurrentScreen('Ranking');
      };
    return(
        <LayoutforAns>
            <CountdownTimer onTimerEnd={handleTimerEnd} time={10}/>
            <div className="text-center p-4">
                <div className="bg-white rounded-full p-4 shadow-lg">
                <div className="text-sm text-gray-500">이번 게임 점수</div>
                <div className="text-3xl font-bold">{point}</div>
                </div>
                <div className="mt-4 bg-white p-4 rounded-lg shadow-md">
                <div className="text-lg">당신의 포인트: {answer.submit == answer.ans? point : "0"}</div>
                </div>
                <div className="mt-4 bg-white p-4 rounded-lg shadow-md">
                <div className="text-lg">다음 게임으로 고고! </div>
                </div>
            </div>
        </LayoutforAns>
    )
}

export default Answer;