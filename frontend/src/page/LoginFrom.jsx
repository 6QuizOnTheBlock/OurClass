import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../components/Layout';
import { getUserInfo } from '../apis/member';
import useUserStore from '../stores/member';
import { useParams } from 'react-router-dom';
import LoginErrorModal from '../components/LoginErrorModal';

const LoginForm = () => {
  const {quizGameId, uuid} = useParams();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [modalOpen,setModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const setUserInfo = useUserStore((state) => state.setUserInfo);
  const setAccessToken = useUserStore((state) => state.setAccessToken);

  const handleLogin = (e) => {
    e.preventDefault();
    console.log("로그인 시도:", email);
    getUserInfo(uuid, email)
    .then((res) => {
      setUserInfo(res.data);
      setAccessToken(res.data.accessToken)
      navigate('/waitingRoom/'+quizGameId);
    })
    .catch((err) => {
      console.error(err);
      setErrorMessage(err.response.data.message);
      setModalOpen(true);
    });
  };

  return (
    <Layout>
      <img
        src="https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/8602d028-bb7c-408a-a3c0-b7da0bb4f2eb_logowithoutBackground.png"
        alt="Teacher"
        className="w-32 h-32 rounded-full mx-auto"
      />
      <div className="mt-4">
        <h1 className="text-lg font-bold">울반 The QUIZ<br/>에 오신 걸 환영합니다!</h1>
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
      <LoginErrorModal
        isOpen={modalOpen}
        message={errorMessage}
        onClose={() => setModalOpen(false)}
      />
    </Layout>
  );
};

export default LoginForm;
