package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.ImageDto;

import java.util.*;

public interface ImageService {

    Image uploadImage(ImageDto image);

    Optional<Image> getImageById(Long imageId);

    void updateImage(ImageDto image,long imageId);
}

