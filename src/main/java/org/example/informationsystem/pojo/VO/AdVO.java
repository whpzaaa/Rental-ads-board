package org.example.informationsystem.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.informationsystem.pojo.entity.Image;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdVO {
    private Long adId;

    private Long userId;

    private String title;

    private String address;

    private String description;

    private int bedroomCount;

    private int bathroomCount;


    private BigDecimal price;


    private Boolean isAvailable;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Image> images;
}
