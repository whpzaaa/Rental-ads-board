package org.example.informationsystem.Controller;


import org.example.informationsystem.Service.ImageService;
import org.example.informationsystem.pojo.DTO.ImageDTO;
import org.example.informationsystem.pojo.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.jacoco.agent.rt.internal_43f5073.core.runtime.AgentOptions.OutputMode.file;

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

    /**
     * 上传图片接口，同时保存图片文件和图片信息
     *
     * @param imageDTO 封装了上传文件及其他参数的 DTO
     * @return 上传成功返回图片信息，否则返回500错误
     */
    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@ModelAttribute ImageDTO imageDTO) throws IOException  {

        Image savedImage = imageService.uploadImage(imageDTO);
        if (savedImage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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