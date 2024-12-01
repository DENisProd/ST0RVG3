package ru.darksecrets.contoller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.darksecrets.dto.response.FileUploadResponse;
import ru.darksecrets.services.DataService;

import java.io.InputStream;

@RequestMapping("api/v1/data")
@RestController
@CrossOrigin(value = "*")
@AllArgsConstructor
public class DataController {
    private final DataService dataService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadData (@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Integer fileSize = (int) file.getSize();

            FileUploadResponse response = dataService.uploadFile(inputStream, file.getOriginalFilename(), fileSize);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            FileUploadResponse errorResponse = new FileUploadResponse(false, null, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
