// Suggested code may be subject to a license. Learn more: ~LicenseLog:1925231942.
package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileController {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/gif"};

    private boolean isValidImageFile(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }
        String contentType = file.getContentType();
        for (String type : ALLOWED_IMAGE_TYPES) {
            if (type.equals(contentType)) {
                return true;
            }
        }
        return false;
    }

    //@PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty() || !isValidImageFile(file)) {
                return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
            }
            if (file.getSize() > 10000000) {
                return new ResponseEntity<>("File is too big", HttpStatus.BAD_REQUEST);
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            String uploadDir = "backend/src/main/resources/static/uploads/";
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                return new ResponseEntity<>("Uploads Directory not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName));
            return new ResponseEntity<>(uploadDir + fileName, HttpStatus.OK);
        }catch (IOException e) {
            return new ResponseEntity<>("Could not upload the file!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
