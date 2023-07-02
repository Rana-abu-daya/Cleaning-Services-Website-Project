package org.ranaabudaya.capstone.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.ranaabudaya.capstone.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
@Slf4j
public class FileService {
    //path to the upload dir, Change it as per your computer
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.uploadDirResume-dir}")
    private String uploadDirResume;
    //this is for profile's photo to the users
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
//the rest to upload the resume
    private final static String FILE_EXTENSION = ".pdf";

    public void encryptPDFFile(String path, String fileName, MultipartFile multipartFile, String clientID) throws IOException {

        boolean isFilePDF = isFilePDF(fileName);
        if (isFilePDF) {
            // Create client directory
            createDirectory(uploadDirResume);
            // Loading the pdf file
            InputStream inputStream = multipartFile.getInputStream();
            PDDocument pdDocument = PDDocument.load(inputStream);
            pdDocument.save(uploadDirResume + "/" + clientID);
            pdDocument.close();
        } else {
            throw new IOException("The file should have PDF format.");
        }
    }


    private static void createDirectory(String dirPath) {
        Path p = Paths.get(dirPath);
        if (!Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createDirectory(p);
            } catch (IOException e) {
                log.error("Creating directory failed: {} ", e.getMessage());
            }
        }
    }

    private static boolean isFilePDF(String fileName) {
        return fileName != null && fileName.endsWith(FILE_EXTENSION);
    }

}

