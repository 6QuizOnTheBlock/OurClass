package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMatchingResponse {

    long id;
    int headCount;
    long leaderId;
    List<MemberSimpleDTO> students;
}