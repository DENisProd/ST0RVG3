package ru.darksecrets.dto.response;

public record FileUploadResponse (
        boolean success,
        String fileName,
        String bucket,
        String error
) {
}
