package com.codesquad.secondhand.command.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {

    String upload(MultipartFile file);

    void delete(String url);
}
