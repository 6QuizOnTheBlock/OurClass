package com.quiz.ourclass.domain.member.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Getter
@Setter
public class DefaultImageRequest {

    long id;
    MultipartFile file;
}
