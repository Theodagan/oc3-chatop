package com.chatop.api.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileService {
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

    public String handleFileUpload(MultipartFile file) {
        try {
            if (file.isEmpty() || !isValidImageFile(file)) {
                return "Please select a file to upload";
            }
            if (file.getSize() > 10000000) {
                return "File is too big";
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            String uploadDir = "/home/user/oc3-chatop/backend/src/main/resources/static/uploads/";
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                return "Uploads Directory not found";
            }
            Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName));
            return "uploads/" + fileName;
        }catch (IOException e) {
            return "Could not upload the file!";
        }
    }
    
}
