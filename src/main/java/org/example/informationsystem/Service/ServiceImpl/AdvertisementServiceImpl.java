package org.example.informationsystem.Service.ServiceImpl;


import org.example.informationsystem.context.BaseContext;
import org.example.informationsystem.pojo.entity.Image;
import org.example.informationsystem.pojo.VO.AdVO;
import org.example.informationsystem.Repository.AdvertisementRepository;
import org.example.informationsystem.Repository.ImageRepository;
import org.example.informationsystem.Result.PageResult;
import org.example.informationsystem.Service.AdvertisementService;
import org.example.informationsystem.pojo.entity.Advertisement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdvertisementServiceImpl  implements AdvertisementService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Override
    public boolean updateAdvertisement(Advertisement advertisement) {
        if (!advertisementRepository.existsById(advertisement.getAdId())) return false;
        advertisementRepository.updateById(advertisement);
        return true;
    }

    @Override
    public PageResult getAllAdvertisements(Pageable pageable) {
        Page<Advertisement> advertisementPage = advertisementRepository.findAll(pageable);
        List<AdVO> list = new ArrayList<>();
        for (Advertisement advertisement : advertisementPage) {
            List<Image> images = imageRepository.getImageByAdId(advertisement.getAdId());
            AdVO adVO = new AdVO();
            BeanUtils.copyProperties(advertisement, adVO);
            adVO.setImages(images);
            list.add(adVO);
        }
        return new PageResult(advertisementPage.getTotalElements(),list);
    }

    @Override
    public Advertisement getAdvertisementById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createAdvertisement(Advertisement advertisement) {
        Long userId = BaseContext.getCurrentId();
        advertisement.setUserId(userId);
        advertisement.setCreatedAt(java.time.LocalDateTime.now());
        advertisement.setUpdatedAt(java.time.LocalDateTime.now());
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
        return savedAdvertisement != null;
    }

    @Override
    public boolean deleteAdvertisement(Long id) {
        if (advertisementRepository.existsById(id)) {
            advertisementRepository.deleteById(id);
            if(imageRepository.existsByAdId(id)){
            imageRepository.deleteByAdId(id);}
            return true;
        }
        return false;
    }


}
