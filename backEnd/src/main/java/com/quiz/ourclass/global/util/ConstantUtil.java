package com.quiz.ourclass.global.util;

public abstract class ConstantUtil {

    public static final String KAFKA_TOPIC = "chat";
    public static final String REDIS_ORG_KEY = "ORGANIZATION:";
    public static final int REDIS_ORG_ALIVE_MINUTE = 10;
    public static final String REDIS_CHAT_ROOM_KEY = "chatRoom:";
    public static final String MONGO_DB_NAME = "chat";
    public static final long HOME_FRIEND_COUNT = 3L;
    public static final String BLACKLIST_ACCESS_TOKEN = "AT:";
    public static final String QUIZ_GAME = "quiz:";
    public static final int RELAY_DEMERIT = -100;
    public static final int RELAY_REWARD = 50;
    public static final Long RELAY_TIMEOUT_DAY = 1L;
    public static final String FCM_KEY_PREFIX = "FCM_";
    public static final int MAX_RETRIES = 5; //최대 재시도 횟수
    public static final long INITIAL_BACKOFF = 1000L; //초기 백오프 시간 (1초)

}
