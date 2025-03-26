package org.example.informationsystem.Service.ServiceImpl;


import org.example.informationsystem.Repository.AdvertisementRepository;
import org.example.informationsystem.Service.AdvertisementService;
import org.example.informationsystem.Entity.DTO.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdvertisementServiceImpl  implements AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Override
    public boolean updateAdvertisement(Advertisement advertisement) {
        if (!advertisementRepository.existsById(advertisement.getAdId())) return false;
        advertisementRepository.updateById(advertisement);
        return true;
    }

    @Override
    public List<Advertisement> getAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    @Override
    public Advertisement getAdvertisementById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createAdvertisement(Advertisement advertisement) {
        advertisement.setCreatedAt(java.time.LocalDateTime.now());
        advertisement.setUpdatedAt(java.time.LocalDateTime.now());
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
        return savedAdvertisement != null;
    }

    @Override
    public boolean deleteAdvertisement(Long id) {
        if (advertisementRepository.existsById(id)) {
            advertisementRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
