package org.example.informationsystem.Controller;


import org.example.informationsystem.Service.ImageService;
import org.example.informationsystem.Entity.DTO.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // 获取所有图片
    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    // 根据房源ID获取所有图片
    @GetMapping("/advertisement/{adId}")
    public ResponseEntity<List<Image>> getImagesByAdId(@PathVariable Long adId) {
        List<Image> images = imageService.getImagesByAdId(adId);
        return images.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(images);
    }

    // 根据ID获取图片
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }

    // 上传图片
    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestBody Image image) {
        boolean success = imageService.uploadImage(image);
        return success ? ResponseEntity.status(HttpStatus.CREATED).body(image)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 删除图片
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        boolean success = imageService.deleteImage(id);
        return success ? ResponseEntity.ok("删除成功") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("图片不存在");
    }
    // 修改图片
    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image) {
        // 检查图片是否存在
        Image existingImage = imageService.getImageById(id);
        if (existingImage == null) {
            return ResponseEntity.notFound().build();  // 图片不存在
        }
        // 更新图片
        boolean success = imageService.updateById(image);  // 调用服务层的更新方法
        return success ? ResponseEntity.ok(image) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}