package ru.darksecrets.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.darksecrets.dto.response.FileUploadResponse;
import ru.darksecrets.models.FileEntity;
import ru.darksecrets.models.FileStatus;
import ru.darksecrets.repository.FileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DataService {
    private final MinioService minioService;
    private FileRepository fileRepository;

    private final String rawDataBucket = "rawdata";

    public FileUploadResponse uploadFile(InputStream inputStream, String fileName, Integer fileSize) {
        try {
            // Загружаем файл в MinIO с помощью MinioService
            minioService.uploadToMinio(inputStream, fileName, rawDataBucket, fileSize);

            // Сохраняем метаданные о файле в PostgreSQL
            FileEntity fileRecord = FileEntity.builder()
                    .fileName(fileName)
                    .fileSize(fileSize)
                    .status(FileStatus.RAW)
                    .build();
            fileRepository.save(fileRecord);

            // Возвращаем успешный ответ
            return new FileUploadResponse(true, fileName, rawDataBucket, null);
        } catch (Exception e) {
            e.printStackTrace();
            // Возвращаем ошибку
            return new FileUploadResponse(false, null, null, e.getMessage());
        }
    }
}
