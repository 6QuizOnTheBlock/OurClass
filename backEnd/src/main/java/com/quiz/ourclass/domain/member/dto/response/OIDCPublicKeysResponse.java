package com.quiz.ourclass.domain.member.dto.response;

import com.quiz.ourclass.domain.member.dto.OIDCPublicKeyDTO;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCPublicKeysResponse {
    List<OIDCPublicKeyDTO> keys;
}
