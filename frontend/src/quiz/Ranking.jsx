import React from 'react';
import Layout from '../components/Layout';

const topUsers = [
  { id: 1, name: 'Moni', points: 453, avatar: '/path-to-avatar1.jpg' },
  { id: 2, name: 'Esha', points: 442, avatar: '/path-to-avatar2.jpg' },
  { id: 3, name: 'Kaoser', points: 433, avatar: '/path-to-avatar3.jpg' }
];

const users = [
  { id: 4, name: 'Moni', points: 223, avatar: '/path-to-avatar4.jpg' },
  { id: 5, name: 'Esha', points: 160, avatar: '/path-to-avatar5.jpg' },
  { id: 6, name: 'Kaoser', points: 140, avatar: '/path-to-avatar6.jpg' },
  { id: 7, name: 'Sam', points: 130, avatar: '/path-to-avatar7.jpg' },
  { id: 8, name: 'Someone', points: 120, avatar: '/path-to-avatar8.jpg' }
];

function RankingScreen() {
  return (
        <Layout>
            <h1 className="text-center text-xl font-bold mb-4">오늘의 퀴즈왕!</h1>
            <div className="flex justify-center space-x-2 mb-4">
                {topUsers.map((user, index) => (
                <div key={user.id} className="flex flex-col items-center">
                    <img className="w-16 h-16 rounded-full" src={user.avatar} alt={user.name} />
                    <div className={`text-${index === 1 ? '2xl' : 'xl'} font-semibold`}>{user.points} pt</div>
                    <div className="text-sm">{index + 1}</div>
                </div>
                ))}
            </div>
            <div className="bg-white p-2 rounded-lg shadow">
                {users.map((user, index) => (
                <div key={user.id} className="flex items-center justify-between p-2">
                    <span>{user.id}</span>
                    <img className="w-10 h-10 rounded-full" src={user.avatar} alt={user.name} />
                    <span>{user.name}</span>
                    <span>{user.points} pt</span>
                </div>
                ))}
            </div>
        </Layout>
  );
}

export default RankingScreen;