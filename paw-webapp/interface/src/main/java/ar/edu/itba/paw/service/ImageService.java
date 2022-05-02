package ar.edu.itba.paw.service;

import java.util.*;

public interface ImageService {

    void updateProfilePicture(long userId, byte[] image);
    Optional<byte[]> getProfilePicture(long userId);
}

