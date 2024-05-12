import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../components/Layout';
import { getUserInfo } from '../apis/member';

const LoginForm = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    // 로그인 로직 추가
    console.log("로그인 시도:", email);
    // 이메일을 서버로 보내서 로그인 처리를 구현해야 함
    getUserInfo("e377b836-fd2e-400d-8de3-14bd148d1d55", email)
    .then((res) => {
      navigate('/waitingRoom')
    })
  };



  return (
    <Layout>
        <img
          src="https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/8602d028-bb7c-408a-a3c0-b7da0bb4f2eb_logowithoutBackground.png"
          alt="Teacher"
          className="w-32 h-32 rounded-full mx-auto"
        />
        <div className="mt-4">
          <h1 className="text-lg font-bold">울반 The QUIZ <br/> 에 오신 걸 환영합니다!</h1>
        </div>
        <form className="mt-4 w-full max-w-xs" onSubmit={handleLogin}>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="이메일을 입력하세요"
            className="p-2 text-lg border-2 border-gray-300 rounded-lg w-full"
          />
          <button
            type="submit"
            className="mt-4 bg-[#152B65] text-white py-2 rounded-full w-full"
          >
            로그인
          </button>
        </form>
    </Layout>
  );
};

export default LoginForm;
