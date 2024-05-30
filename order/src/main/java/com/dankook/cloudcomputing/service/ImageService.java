package com.dankook.cloudcomputing.service;

import com.dankook.cloudcomputing.entity.Image;
import com.dankook.cloudcomputing.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public Long save(Image image){
        return imageRepository.save(image).getId();
    }

    public List<Image> findByUserId(Long userId){
        return imageRepository.findByUserId(userId);
    }

    public void deleteById(Long id){
        imageRepository.deleteById(id);
    }
}
