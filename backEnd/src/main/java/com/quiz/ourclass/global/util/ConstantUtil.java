package com.quiz.ourclass.global.util;

public abstract class ConstantUtil {

    public static final String KAFKA_TOPIC = "chat";
    public static final String REDIS_ORG_KEY = "ORGANIZATION:";
    public static final int REDIS_ORG_ALIVE_MINUTE = 10;
    public static final String REDIS_CHAT_ROOM_KEY = "chatRoom:";
    public static final long HOME_FRIEND_COUNT = 3L;
    public static final String BLACKLIST_ACCESS_TOKEN = "AT:";
    public static final String QUIZ_GAME = "quiz:";
    public static final int RELAY_DEMERIT = -100;
    public static final int RELAY_REWARD = 50;
    public static final Long RELAY_TIMEOUT_DAY = 1L;
    public static final String FCM_KEY_PREFIX = "FCM_";
    public static final String QUIZ_GAMER = "gamer";
    public static final String QUIZ_QUESTION = "question";
    public static final String RANKING = "ranking";
    public static final String QUIZ_ANSWER = "answer";
    public static final String AUTHORIZATION = "Authorization";
    public static final String REDIS_GROUP_KEY = "CHALLENGE_LEADER:";
    public static final Long DEFAULT_TIMEOUT = 60L * 1000 * 20;
    public static final Long REDIRECT_TIME = 5L * 1000;

    public static final String EXCEPTION_ATTRIBUTE = "exception";

    // 인스턴스화 방지
    private ConstantUtil() {
        throw new UnsupportedOperationException(
            "This is a utility class and cannot be instantiated");
    }
}
