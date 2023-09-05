package com.codesquad.secondhand.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface CloudStorageService {

    String upload(MultipartFile file);

    void delete(String url);
}
