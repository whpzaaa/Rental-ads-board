package org.example.informationsystem.Service;


import org.example.informationsystem.Entity.DTO.Image;

import java.util.List;

public interface ImageService {
    List<Image> getAllImages();

    Image getImageById(Long id);

    boolean uploadImage(Image image);

    boolean deleteImage(Long id);

    List<Image> getImagesByAdId(Long adId);

    boolean updateById(Image image);
}
