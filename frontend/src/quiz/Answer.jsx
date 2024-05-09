import CountdownTimer from "../components/CountdownTime";
import LayoutforAns from "../components/LayoutforAns";
import { Button } from "../components/ui/button";
import { useNavigate } from "react-router-dom";
import { useParams } from 'react-router-dom';
const Answer = () => {
    const navigate = useNavigate();
    const {Qid} = useParams();
    const handleTimerEnd = () => {

        var next = parseInt(Qid) + 1;
        
        if(parseInt(Qid) > 5){
            navigate("/quizEnd");
        }else{
            parseInt(Qid)%2 === 0? navigate("/subject/"+ next) :  navigate("/multiple/"+ next)
        }
      };
    return(
        <LayoutforAns>
            <CountdownTimer onTimerEnd={handleTimerEnd}/>
            <div className="text-center p-4">
                <div className="bg-white rounded-full p-4 shadow-lg">
                <div className="text-sm text-gray-500">YOUR SCORE</div>
                <div className="text-3xl font-bold">100 pt</div>
                </div>
                <div className="mt-4 bg-white p-4 rounded-lg shadow-md">
                <div className="text-lg">정답: 여자,바람,돌</div>
                </div>
            </div>
            <div className="w-full p-4">
                <Button className="bg-[#A3172D] text-white w-full py-3 rounded-lg mb-2 ">
                 오답모음!
                </Button>
                <Button className=" text-white w-full py-3 rounded-lg mb-2 hover:bg-[#A3172D]">
                돌 하르방, 참돔, 야자수
                </Button>
                <Button className=" text-white w-full py-3 rounded-lg hover:bg-[#A3172D]">
                에라 모르겠다.
                </Button>
            </div>
        </LayoutforAns>
    )
}

export default Answer;