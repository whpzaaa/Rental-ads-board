package org.example.informationsystem.Repository;

import jakarta.transaction.Transactional;
import org.example.informationsystem.Entity.DTO.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> getImageByAdId(Long adId);
    @Modifying
    @Transactional
    @Query("UPDATE Image i SET " +
            "i.filePath = :#{#image.filePath}, " +
            "i.fileName = :#{#image.fileName}, " +
            "i.displayOrder = :#{#image.displayOrder}, " +
            "i.isPrimary = :#{#image.isPrimary} " +
            "WHERE i.imageId = :#{#image.imageId}")
    void updateById(@Param("image") Image image);
}
