package org.example.informationsystem.Repository;

import jakarta.transaction.Transactional;
import org.example.informationsystem.pojo.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Advertisement a SET " +
            "a.title = :#{#advertisement.title}, " +
            "a.address = :#{#advertisement.address}, " +
            "a.description = :#{#advertisement.description}, " +
            "a.bedroomCount = :#{#advertisement.bedroomCount}, " +
            "a.bathroomCount = :#{#advertisement.bathroomCount}, " +
            "a.price = :#{#advertisement.price}, " +
            "a.isAvailable = :#{#advertisement.isAvailable}, " +
            "a.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE a.adId = :#{#advertisement.adId}")
    void updateById(@Param("advertisement") Advertisement advertisement);

}
