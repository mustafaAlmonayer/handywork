package com.mustafa.grad.handywork.service;

// Import the required packages

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.SneakyThrows;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageService {

    Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @SneakyThrows
    public String imageUpload(MultipartFile image) {

        Path temp;

        try {
            temp = Files.createTempFile("target", ".file");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        image.transferTo(temp);

        return cloudinary.uploader().upload(temp.toFile(), ObjectUtils.emptyMap()).get("url").toString();

    }

    public List<String> imageUpload(List<MultipartFile> images) {

        List<String> urls = new ArrayList<>();

        for (MultipartFile image : images) {
            urls.add(imageUpload(image));
        }

        return urls;
    }

}
