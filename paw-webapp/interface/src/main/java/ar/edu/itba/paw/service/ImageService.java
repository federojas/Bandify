package ar.edu.itba.paw.service;

import java.io.IOException;

public interface ImageService {

    void updateProfilePicture(long userId, byte[] image);

    byte[] getProfilePicture(long userId, boolean isBand) throws IOException;
}

