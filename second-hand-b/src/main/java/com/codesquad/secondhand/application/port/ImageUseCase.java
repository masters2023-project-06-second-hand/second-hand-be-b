package com.codesquad.secondhand.application.port;

import com.codesquad.secondhand.application.port.in.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUseCase {

    /**
     * 이미지를 클라우드에 업로드하고 DB에 저장한다.
     *
     * @param file 업로드 할 이미지 파일
     * @return DB에 저장한 이미지 ID와 업로드한 이미지 URL을 담은 객체
     */
    ImageUploadResponse upload(MultipartFile file);

    /**
     * 이미지를 DB와 클라우드에서 삭제한다.
     *
     * @param id 삭제할 이미지의 식별자 ID
     */
    void delete(Long id);
}
