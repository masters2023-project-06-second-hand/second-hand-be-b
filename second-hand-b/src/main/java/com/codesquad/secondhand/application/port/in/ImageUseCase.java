package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.request.DeleteImageRequest;
import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUseCase {

    /**
     * 이미지를 클라우드에 업로드한다.
     *
     * @param file 업로드 할 이미지 파일
     * @return 업로드한 이미지 URL
     */
    String uploadCloud(MultipartFile file);

    /**
     * 이미지를 DB에 저장한다.
     *
     * @param imgUrl 업로드 할 이미지 url
     * @return DB에 저장한 이미지 ID와 이미지 URL을 담은 객체
     */
    ImageInfo save(String imgUrl);

    /**
     * 이미지를 DB에서 삭제한다.
     */
    void delete(DeleteImageRequest deleteImageRequest);
}
