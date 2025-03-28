package org.example.informationsystem.Service;



import org.example.informationsystem.pojo.entity.Advertisement;
import org.example.informationsystem.Result.PageResult;
import org.springframework.data.domain.Pageable;

public interface AdvertisementService {
    boolean updateAdvertisement(Advertisement advertisement);

    PageResult getAllAdvertisements(Pageable pageable);

    Advertisement getAdvertisementById(Long id);

    boolean createAdvertisement(Advertisement advertisement);

    boolean deleteAdvertisement(Long id);

}
