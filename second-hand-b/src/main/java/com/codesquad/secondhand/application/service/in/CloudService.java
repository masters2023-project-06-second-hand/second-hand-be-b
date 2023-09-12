package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.CloudUseCase;
import com.codesquad.secondhand.application.port.out.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CloudService implements CloudUseCase {

    private final CloudStorageService cloudStorageService;

    @Override
    public String uploadCloud(MultipartFile file) {
        return cloudStorageService.upload(file);
    }
}
