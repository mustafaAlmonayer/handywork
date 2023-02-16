package com.mustafa.grad.handywork.utils;

import com.mustafa.grad.handywork.service.ImageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public class ImageUtils {
    public static List<String> ImagesToUrls (List<MultipartFile> images) {
        ImageService imageService = new ImageService();
        imageService.init();
        return imageService.imageUpload(images);
    }

    public static String ImageToUrl (MultipartFile image) {
        ImageService imageService = new ImageService();
        imageService.init();
        return imageService.imageUpload(image);
    }

}
