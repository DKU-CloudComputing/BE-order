package com.dankook.cloudcomputing.controller;

import com.dankook.cloudcomputing.dto.RequestDto;
import com.dankook.cloudcomputing.entity.Image;
import com.dankook.cloudcomputing.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final ImageService imageService;

    @PostMapping("/api/creating")
    public String createImage(RequestDto requestDto){
        log.info("request: /api/creating query: {}", requestDto.getQuery());
        log.info("request: /api/creating size: {}", requestDto.getSize());
        log.info("request: /api/creating userId: {}", requestDto.getUserId());

        URI uri = UriComponentsBuilder
                .fromUriString("https://true-porpoise-uniformly.ngrok-free.app")
                .path("/image/creating")
                .queryParam("query", requestDto.getQuery())
                .queryParam("size", requestDto.getSize())
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(uri, byte[].class);

        byte[] imageBytes = responseEntity.getBody();
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        imageService.save(new Image(requestDto.getQuery(), encodedImage, requestDto.getUserId()));

        return encodedImage;
    }

    @PostMapping("/api/editing")
    public String editImage(RequestDto requestDto){

        log.info("request: /api/editing query: {}", requestDto.getQuery());
        log.info("request: /api/editing image: {}", requestDto.getImage());
        log.info("request: /api/editing size: {}", requestDto.getSize());
        log.info("request: /api/editing userId: {}", requestDto.getUserId());

        URI uri = UriComponentsBuilder
                .fromUriString("https://true-porpoise-uniformly.ngrok-free.app")
                .path("/image/editing")
                .encode()
                .build()
                .toUri();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("query", requestDto.getQuery());
        requestBody.add("image", requestDto.getImage());
        requestBody.add("size", requestDto.getSize());

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(uri, requestBody, byte[].class);

        byte[] imageBytes = responseEntity.getBody();
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

        imageService.save(new Image(requestDto.getQuery(), encodedImage, requestDto.getUserId()));

        return encodedImage;
    }

    @GetMapping("/api/user/image")
    public List<Image> getImage(@RequestParam("userId") Long userId) {
        List<Image> images = imageService.findByUserId(userId);
        return images;
    }

    @CrossOrigin
    @DeleteMapping("/api/user/image/{id}")
    public void deleteImage(@PathVariable("id") Long id){
        log.info("request: /api/user/image/{} DELETE", id);
        imageService.deleteById(id);
    }

}
