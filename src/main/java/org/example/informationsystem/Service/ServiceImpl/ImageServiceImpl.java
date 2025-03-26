package org.example.informationsystem.Service.ServiceImpl;



import org.example.informationsystem.Repository.ImageRepository;
import org.example.informationsystem.Service.ImageService;
import org.example.informationsystem.Entity.DTO.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean uploadImage(Image image) {
        image.setUploadTime(java.time.LocalDateTime.now());
        Image saveImage = imageRepository.save(image);
        return saveImage != null;
    }

    @Override
    public boolean deleteImage(Long id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Image> getImagesByAdId(Long adId) {
         return imageRepository.getImageByAdId(adId);
    }

    @Override
    public boolean updateById(Image image) {
        if (!imageRepository.existsById(image.getImageId())) return false;
        imageRepository.updateById(image);
        return true;
    }
}
