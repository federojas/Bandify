package ar.edu.itba.paw.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public interface ImageService {

    void updateProfilePicture(long userId, byte[] image);
    byte[] getProfilePicture(long userId, boolean isBand) throws IOException;
}

