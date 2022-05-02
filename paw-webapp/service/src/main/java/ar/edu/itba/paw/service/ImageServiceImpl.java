package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Objects;
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
        if (image.length > 0)
            imageDao.updateProfilePicture(userId, image);
    }

    @Override
    public byte[] getProfilePicture(long userId, boolean isBand) throws IOException {
        Optional<byte[]> optionalBytes = imageDao.getProfilePicture(userId);
        if(!optionalBytes.isPresent()) {
            File file;
            if(isBand)
                file = ResourceUtils.getFile("classpath:images/band.jpg");
            else
                file = ResourceUtils.getFile("classpath:images/artist.png");
            InputStream fileStream = new FileInputStream(file);
            return IOUtils.toByteArray(Objects.requireNonNull(fileStream));
        }
        return optionalBytes.get();
    }
}
