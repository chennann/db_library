package com.chennann.library.controller;

import com.chennann.library.pojo.Result;
import com.chennann.library.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload (MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = AliOssUtil.uploadFile("library/" + filename, file.getInputStream());
        return Result.success(url);
    }
}
