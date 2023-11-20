package com.example.cometcanvasbackend.Services;

import com.example.cometcanvasbackend.Entities.Photo;
import com.example.cometcanvasbackend.Repositories.PhotoRepository;
import com.example.cometcanvasbackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Optional<Photo> getPhotoById(Long id) {
        return photoRepository.findById(id);
    }

    public void uploadPhoto(Photo photo, Long userId) {
        photo.setUser(userRepository.findById(userId).get());
        photoRepository.save(photo);
    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public Optional<byte[]> getPhotoImageDataById(Long id) {
        Optional<Photo> optionalPhoto = this.getPhotoById(id);

        return optionalPhoto.map(Photo::getImageData);
    }

}
