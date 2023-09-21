package com.codesquad.secondhand.adapter.out.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codesquad.secondhand.application.port.out.FileStoragePort;
import com.codesquad.secondhand.application.service.in.common.exception.S3UploadFailedException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3StorageAdapter implements FileStoragePort {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new S3UploadFailedException();
        }
    }

    public void delete(String url) {
        amazonS3Client.deleteObject(bucket, url);
    }
}
