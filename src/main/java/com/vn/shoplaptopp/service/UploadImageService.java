package com.vn.shoplaptopp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadImageService {

    private ServletContext servletContext;

    public UploadImageService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleSaveUploadImageService(MultipartFile file, String targetFolder) {
        if (file.isEmpty()) {
            return "";
        }
        String rootPath = this.servletContext.getRealPath("/resources/admin/images");
        String finalName = "";
        byte[] bytes;
        try {
            bytes = file.getBytes();
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // Create the file on server
            String subpath = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator
                    + subpath);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            finalName = subpath;
        } catch (

        IOException e) {
            e.printStackTrace();
        }
        return finalName;
    }
}
