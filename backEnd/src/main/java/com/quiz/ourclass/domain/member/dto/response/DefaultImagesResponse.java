package com.quiz.ourclass.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class DefaultImagesResponse {

    private String studentManImage;
    private String studentWomanImage;
    private String teacherManImage;
    private String teacherWomanImage;

    public static DefaultImagesResponse of(String studentManImage, String studentWomanImage,
        String teacherManImage, String teacherWomanImage) {
        return builder()
            .studentManImage(studentManImage)
            .studentWomanImage(studentWomanImage)
            .teacherManImage(teacherManImage)
            .teacherWomanImage(teacherWomanImage)
            .build();
    }
}
