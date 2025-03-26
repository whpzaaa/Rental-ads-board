package org.example.informationsystem.Service;



import org.example.informationsystem.Entity.DTO.Advertisement;

import java.util.List;

public interface AdvertisementService {
    boolean updateAdvertisement(Advertisement advertisement);

    List<Advertisement> getAllAdvertisements();

    Advertisement getAdvertisementById(Long id);

    boolean createAdvertisement(Advertisement advertisement);

    boolean deleteAdvertisement(Long id);
}
