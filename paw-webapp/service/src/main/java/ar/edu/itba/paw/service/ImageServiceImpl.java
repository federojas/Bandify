package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(final ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public void updateProfilePicture(long userId, byte[] image) {
        imageDao.updateProfilePicture(userId, image);
    }

    @Override
    public void deleteProfilePicture(long userId) {
        imageDao.deleteProfilePicture(userId);
    }

    @Override
    public void createProfilePicture(long userId, byte[] image) {
        imageDao.crateProfilePicture(userId, image);
    }

    @Override
    public Optional<byte[]> getProfilePicture(long userId) {
        return imageDao.getProfilePicture(userId);
    }
}
