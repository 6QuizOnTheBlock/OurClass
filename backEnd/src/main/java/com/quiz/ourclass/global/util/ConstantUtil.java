package com.quiz.ourclass.global.util;

public abstract class ConstantUtil {

    public static final String KAFKA_TOPIC = "chat";
    public static final String REDIS_ORG_KEY = "ORGANIZATION:";
    public static final int REDIS_ORG_ALIVE_MINUTE = 10;
    public static final String REDIS_CHAT_ROOM_KEY = "chatRoom:";
    public static final String MONGO_DB_NAME = "chat";
    public static final long HOME_FRIEND_COUNT = 3L;
    public static final int RELAY_DEMERIT = -100;
    public static final Long RELAY_TIMEOUT_DAY = 1L;
}
