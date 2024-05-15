// src/context/WebSocketContext.js
import React, { createContext, useContext, useEffect, useState } from 'react';
import SockJS from "sockjs-client/dist/sockjs";
import Stomp from 'stompjs';
import useUserStore from '../stores/member';

const WebSocketContext = createContext(null);

export const WebSocketProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);
  const accessToken = useUserStore(state => state.accessToken);

  useEffect(() => {

    if(!accessToken) {
      console.log("아직 AccessToken이 null 입니다. 로그인 필요");
      return;
    }

    const sockJS = new SockJS("http://localhost:8080/api/ws-stomp");
    const client = Stomp.over(sockJS);
    client.connect(
      {
        Authorization: accessToken,
        type: "quiz"
      },
      () => {
        console.log("STOMP 웹소켓에 연결되었습니다.");
        setStompClient(client);
      },
      (error) => {
        console.error("STOMP 웹소켓 연결 오류:", error);
      }
    );

    return () => {
      if (client) {
        client.disconnect(() => {
          console.log("STOMP 웹소켓에서 연결 해제되었습니다.");
        });
      }
    };
  }, [accessToken]);

  return (
    <WebSocketContext.Provider value={{ stompClient }}>
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => useContext(WebSocketContext);
