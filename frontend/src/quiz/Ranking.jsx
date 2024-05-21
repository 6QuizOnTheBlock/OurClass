import React, { useEffect, useState } from 'react';
import Layout from '../components/Layout';
import { getRanking } from '../apis/ranking';
import CountdownTimer from '../components/CountdownTime';


function RankingScreen({currentScreen, setCurrentScreen, Qid, nextQuestion}) {
  const [users,setUsers] = useState([]);
  const handleTimerEnd = () => {
    nextQuestion();
    setCurrentScreen('Subject');
  };

  useEffect(() => {
    getRanking(Qid)
    .then((res) => {
      setUsers(() => [...res.data])
    })

  },[currentScreen])


  return (
        <Layout>
          <CountdownTimer onTimerEnd={handleTimerEnd} time={10}/>
            <h1 className="text-center text-xl font-bold mb-4">오늘의 퀴즈왕!</h1>
            <div className="bg-white p-2 rounded-lg shadow">
                {users.map((user, index) => (
                <div key={user.id} className="flex items-center justify-between p-2">
                    <span>{user.id}</span>
                    <img className="w-10 h-10 rounded-full" src={user.photo} alt={user.name} />
                    <span>{user.name}</span>
                    <span>{user.point} pt</span>
                </div>
                ))}
            </div>
        </Layout>
  );
}

export default RankingScreen;