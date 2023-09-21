package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.application.port.in.command.FileUseCase;
import com.codesquad.secondhand.application.port.out.FileStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileService implements FileUseCase {

    private final FileStoragePort fileStoragePort;

    @Override
    public String uploadCloud(MultipartFile file) {
        return fileStoragePort.upload(file);
    }
}
