package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupServiceImpl implements GroupService {

    private final UserAccessUtil accessUtil;
    private final RedisUtil redisUtil;
    private final static String REDIS_GROUP_KEY = "CHALLENGE_LEADER:";

    @Transactional
    @Override
    public String createMatchingRoom(long challengeId) {
        long Memberid = accessUtil.getMember().getId();
        String dataKey = makeGroupKey(challengeId, Memberid);
        redisUtil.setAdd(dataKey, String.valueOf(Memberid));
        return dataKey;
    }

    private static String makeGroupKey(long challengeId, long Memberid) {
        return REDIS_GROUP_KEY + challengeId + "_" + Memberid;
    }
}
