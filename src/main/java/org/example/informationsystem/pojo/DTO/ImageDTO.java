package org.example.informationsystem.pojo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {

    // 用于接收上传的文件
    private MultipartFile file;

    // 广告ID
    private Long adId;

    // 显示顺序，默认0
    private int displayOrder = 0;

    // 是否主图，默认false
    private Boolean isPrimary = false;

    // Getters and Setters

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
}