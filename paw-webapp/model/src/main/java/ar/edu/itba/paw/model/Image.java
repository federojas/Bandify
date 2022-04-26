package ar.edu.itba.paw.model;

public class Image {

    private long id;
    private byte[] data;
    private String mimeType;

    public Image(long id, byte[] data, String mimeType) {
        this.id = id;
        this.data = data;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

}
