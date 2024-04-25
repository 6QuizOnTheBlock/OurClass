package com.quiz.ourclass.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Data
public class AwsS3ObjectStorage {

    private AmazonS3 amazonS3; //AmazonS3 config 미리 빈 주입
    private String bucket; //빈 주입 시 setter
    private String aiS3Url; //빈 주입 시 setter

    public AwsS3ObjectStorage(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        // UUID 이용해 고유한 파일명 생성
        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public int deleteFile(String fileUrl) {
        try {
            // URL에서 객체 키 추출
            URL url = new URL(fileUrl);
            // URL의 첫 번째 '/'를 제거하여 객체 키 얻기
            String key = url.getPath().substring(1);

            // 파일 존재 여부 확인
            if (amazonS3.doesObjectExist(bucket, key)) {
                // S3에서 파일 삭제
                amazonS3.deleteObject(bucket, key);
                log.info("File deleted successfully: {}", key);
                return 1;
            } else { // file not found
                log.warn("File not found: {}", key);
                throw new GlobalException(ErrorCode.FILE_NOT_FOUND);
            }
        } catch (Exception e) { //error
            log.error("Failed to delete file!: {}", fileUrl, e);
            throw new GlobalException(ErrorCode.AWS_SERVER_ERROR);
        }
    }
}
