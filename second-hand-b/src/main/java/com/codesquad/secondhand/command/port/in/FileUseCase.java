package com.codesquad.secondhand.command.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface FileUseCase {

    /**
     * 이미지를 클라우드에 업로드한다.
     *
     * @param file 업로드 할 이미지 파일
     * @return 업로드한 이미지 URL
     */
    String uploadCloud(MultipartFile file);
}
