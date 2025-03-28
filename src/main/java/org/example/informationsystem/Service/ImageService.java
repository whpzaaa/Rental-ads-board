package org.example.informationsystem.Service;


import org.example.informationsystem.pojo.DTO.ImageDTO;
import org.example.informationsystem.pojo.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> getAllImages();

    Image getImageById(Long id);


    boolean deleteImage(Long id);

    List<Image> getImagesByAdId(Long adId);

    boolean updateById(Image image);

    Image uploadImage(ImageDTO imageDTO) throws IOException;

    String storeFile(MultipartFile file) throws IOException;
}
