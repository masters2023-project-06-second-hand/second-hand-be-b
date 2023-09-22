package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.port.in.FileUseCase;
import com.codesquad.secondhand.command.port.out.FileStoragePort;
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
