package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface ImageDao {
    void updateProfilePicture(long userId, byte[] image);

    Optional<byte[]> getProfilePicture(long userId);
}
