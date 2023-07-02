package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    //path to the upload dir, Change it as per your computer
    @Value("${file.upload-dir}")
    private String uploadDir;
    public void uploadFile(MultipartFile file) {
        try {
            System.out.println(uploadDir);
            Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileName  = file.getOriginalFilename();
            int filelength = file.getBytes().length;
            System.out.println("File uploaded successfully, " + "file name is :: "+ fileName + " and length is ::" + filelength);

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("File Not Found");
        }
    }
}
