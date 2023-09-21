package com.codesquad.secondhand.command.port.in;

import com.codesquad.secondhand.command.adapter.in.web.image.request.DeleteImageRequest;
import com.codesquad.secondhand.command.adapter.in.web.image.response.ImageInfo;

public interface ImageUseCase {

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
