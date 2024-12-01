package ru.darksecrets.services;

import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.darksecrets.configuration.MinioClientConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MinioService {

    public void uploadToMinio(InputStream inputStream, String fileName, String bucketName, Integer fileSize) throws Exception {
        MinioClient minioClient = MinioClientConfig.getMinioClient();

        // Используем указанный или дефолтный бакет
        String bucket = (bucketName != null) ? bucketName : "rawdata";

        // Параметры загрузки файла
        PutObjectArgs objectArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .stream(inputStream, fileSize, -1)
                .contentType("application/octet-stream")
                .build();

        // Загружаем файл в MinIO
        minioClient.putObject(objectArgs);
    }

//    public Map<String, Object> uploadFile(InputStream inputStream, String fileName, String bucketName, Integer fileSize) {
//        Map<String, Object> resultEntity = new HashMap<>();
//        try {
//            MinioClient minioClient = MinioClientConfig.getMinioClient();
//
//            PutObjectArgs objectArgs = PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(fileName)
//                    .stream(inputStream, fileSize, -1)
////                    .contentType(contentType != null ? contentType : "application/octet-stream")
//                    .build();
//
//            minioClient.putObject(objectArgs);
//
//            resultEntity.put("success", true);
//            resultEntity.put("fileName", fileName);
//            resultEntity.put("bucketName", bucketName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultEntity.put("success", false);
//            resultEntity.put("error", e.getMessage());
//        }
//        return resultEntity;
//    }



    public boolean bucketExists(String bucketName) {
        boolean flag = false;
        try {
            flag = MinioClientConfig.bucketExists(bucketName);
            if (flag) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public InputStream getFileInputStream(String fileName, String bucketName) {
        try {
            MinioClient minioClient = MinioClientConfig.getMinioClient();
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }


    public void createBucketName(String bucketName) {
        try {
            if (bucketName.isEmpty()) {
                return;
            }
            MinioClient minioClient = MinioClientConfig.getMinioClient();
            boolean isExist = MinioClientConfig.bucketExists(bucketName);
            if (isExist) {
                log.info("Bucket {} already exists.", bucketName);
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

//    public FileDTO getFile(String bucketName, String originalName) {
//        try {
//            MinioClient minioClient = MinioClientConfig.getMinioClient();
//
//            GetObjectResponse getObjectResponse = minioClient.getObject(
//                    GetObjectArgs.builder().bucket(bucketName).object(originalName).build()
//            );
//
//            byte[] fileContent = getObjectResponse.readAllBytes();
//
//            String mimeType = URLConnection.guessContentTypeFromName(originalName);
//            if (mimeType == null) {
//                mimeType = "application/octet-stream";
//            }
//
//            return new FileDTO(fileContent, mimeType);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void deleteBucketName(String bucketName) {
        try {
            if (bucketName.isEmpty()) {
                return;
            }
            MinioClient minioClient = MinioClientConfig.getMinioClient();
            boolean isExist = MinioClientConfig.bucketExists(bucketName);
            if (isExist) {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public void deleteBucketFile(String bucketName) {
        try {
            if (bucketName.isEmpty()) {
                return;
            }
            MinioClient minioClient = MinioClientConfig.getMinioClient();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                minioClient.deleteBucketEncryption(DeleteBucketEncryptionArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public String getPreviewFileUrl(String bucketName, String fileName) {
        try {
            MinioClient minioClient = MinioClientConfig.getMinioClient();
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
