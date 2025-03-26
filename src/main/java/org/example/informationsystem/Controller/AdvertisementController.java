package org.example.informationsystem.Controller;


import jakarta.validation.Valid;
import org.example.informationsystem.Service.AdvertisementService;
import org.example.informationsystem.Entity.DTO.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    // 获取所有房源
    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();
        return ResponseEntity.ok(advertisements);
    }

    // 根据ID获取房源
    @GetMapping("/{id}")
    public ResponseEntity<Advertisement> getAdvertisementById(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.getAdvertisementById(id);
        return advertisement != null ? ResponseEntity.ok(advertisement) : ResponseEntity.notFound().build();
    }

    // 创建房源
    @PostMapping
    public ResponseEntity<Advertisement> createAdvertisement(@Valid @RequestBody Advertisement advertisement) {
        boolean success = advertisementService.createAdvertisement(advertisement);
        return success ? ResponseEntity.status(HttpStatus.CREATED).body(advertisement)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 更新房源信息
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdvertisement(@PathVariable Long id, @RequestBody Advertisement advertisement) {
        boolean success = advertisementService.updateAdvertisement(advertisement);
        return success ? ResponseEntity.ok("更新成功") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("更新失败，房源不存在");
    }

    // 删除房源
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdvertisement(@PathVariable Long id) {
        boolean success = advertisementService.deleteAdvertisement(id);
        return success ? ResponseEntity.ok("删除成功") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败，房源不存在");
    }
}
