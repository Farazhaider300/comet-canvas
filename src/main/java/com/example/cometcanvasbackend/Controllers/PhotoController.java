package com.example.cometcanvasbackend.Controllers;

import com.example.cometcanvasbackend.Entities.Photo;
import com.example.cometcanvasbackend.Services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/comet-canvas/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllPhotos() {
//        List<Photo> photos = photoService.getAllPhotos();
//
//        if (!photos.isEmpty()) {
//            return new ResponseEntity<>(photos, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("No photos found", HttpStatus.NOT_FOUND);
//        }
//    }


    @GetMapping("/{id}/details")
    public ResponseEntity<Photo> getPhotoDetailsById(@PathVariable Long id) {
        Optional<Photo> optionalPhoto = photoService.getPhotoById(id);
        return optionalPhoto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable Long id) {
        Optional<Photo> optionalPhoto = photoService.getPhotoById(id);
        return optionalPhoto.map(photo -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type (e.g., IMAGE_JPEG, IMAGE_PNG, etc.)
            headers.setContentLength(photo.getImageData().length);
            return new ResponseEntity<>(photo.getImageData(), headers, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.noContent().build());
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllPhotos() {
        List<Photo> photos = photoService.getAllPhotos();
        List<Map<String, Object>> responseDataList = new ArrayList<>();

        for (Photo photo : photos) {
            Optional<byte[]> optionalImageData = photoService.getPhotoImageDataById(photo.getId());

            if (optionalImageData.isPresent()) {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("photoId", photo.getId());
                responseData.put("imageData", optionalImageData.get());

                responseDataList.add(responseData);
            }
        }

        if (!responseDataList.isEmpty()) {
            return new ResponseEntity<>(responseDataList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No photos found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Long id) {
        Optional<Photo> optionalPhoto = photoService.getPhotoById(id);

        return optionalPhoto.map(photo -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(photo.getImageData().length);
            headers.setContentDispositionFormData("attachment", "photo-" + id + ".jpg"); // Set the filename

            return new ResponseEntity<>(photo.getImageData(), headers, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long userId, @RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
        try {
            Photo photo = new Photo(title, author, description, file.getBytes());
            photoService.uploadPhoto(photo, userId);
            return ResponseEntity.ok("Photo uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading photo: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePhoto(@PathVariable Long id, @RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("description") String description) throws IOException {
        Optional<Photo> optionalPhoto = photoService.getPhotoById(id);

        if (optionalPhoto.isPresent()) {
            Photo existingPhoto = optionalPhoto.get();
            existingPhoto.setTitle(title);
            existingPhoto.setAuthor(author);
            existingPhoto.setDescription(description);
//            existingPhoto.setImageData(file.getBytes());
            photoService.uploadPhoto(existingPhoto,existingPhoto.getUser().getId());
            return ResponseEntity.ok("Photo updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Photo not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePhoto(@PathVariable Long id) {
        Optional<Photo> optionalPhoto = photoService.getPhotoById(id);

        if (optionalPhoto.isPresent()) {
            photoService.deletePhoto(id);
            return ResponseEntity.ok("Photo deleted successfully!");
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
