package com.quiz.ourclass.domain.board.mapper;

import com.quiz.ourclass.domain.board.entity.Image;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {


    void updateImage(
        String originalName, String path, LocalDateTime createTime,
        @MappingTarget Image image
    );
}
