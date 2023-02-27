package com.mustafa.grad.handywork.utils;

import com.mustafa.grad.handywork.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageUtils {

    @Autowired
    ImageService  imageService;

    public List<String> ImagesToUrls (List<MultipartFile> images) {

        return imageService.imageUpload(images);

    }

    public String ImageToUrl (MultipartFile image) {

        return imageService.imageUpload(image);

    }

}
