package org.example.informationsystem.Service.ServiceImpl;



import org.example.informationsystem.Repository.ImageRepository;
import org.example.informationsystem.Service.ImageService;
import org.example.informationsystem.pojo.DTO.ImageDTO;
import org.example.informationsystem.pojo.entity.Image;
import org.example.informationsystem.utills.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
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

    @Override
    public Image uploadImage(ImageDTO imageDTO) throws IOException {
        Long adId = imageDTO.getAdId();
        MultipartFile file = imageDTO.getFile();
        int displayOrder = imageDTO.getDisplayOrder();
        Boolean isPrimary = imageDTO.getIsPrimary();
        // 保存文件，获取存储的路径
        String filePath = storeFile(file);

        // 构造 Image 对象，并设置属性
        Image image = new Image();
        image.setAdId(adId);
        image.setFileName(file.getOriginalFilename());
        image.setFilePath(filePath);
        image.setDisplayOrder(displayOrder);
        image.setIsPrimary(isPrimary);
        image.setUploadTime(LocalDateTime.now());

        // 保存记录到数据库
        return imageRepository.save(image);
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        //获取文件的原始名 （****.***）
        String originalFilename = file.getOriginalFilename();
        //从最后一个.的索引位置开始截取原始名得到文件名后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //通过uuid获取随机字符串并与后缀进行拼接 得到上传到阿里云的文件名
        String objectName = UUID.randomUUID().toString() + extension;
        //调用aliossutill工具类的upload方法 获取文件访问路径 返回给前端 前端通过访问该路径进行页面展示
        String path = aliOssUtil.upload(file.getBytes(), objectName);
        return path;
    }
}
