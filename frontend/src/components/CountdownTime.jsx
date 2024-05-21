import React, { useState, useEffect } from 'react';

function CountdownTimer({onTimerEnd, time}) {
  const [timeLeft, setTimeLeft] = useState(time); // 10초로 초기화

  useEffect(() => {
    if (timeLeft > 0) {
        // 1초씩 감소
        const timer = setTimeout(() => setTimeLeft(timeLeft - 1), 1000);
        // 컴포넌트 언마운트 시, timer 초기화 
        return () => clearTimeout(timer); 
      } else {
        // 타이머가 0에 도달하면 부모 컴포넌트에 종료를 알림
        onTimerEnd();
      }
  }, [timeLeft]);

  return (
    <div className="border-2 w-20 h-20  rounded-lg flex items-center justify-center text-white text-4xl mx-auto">
      {timeLeft} 
    </div>
  );
}

export default CountdownTimer;