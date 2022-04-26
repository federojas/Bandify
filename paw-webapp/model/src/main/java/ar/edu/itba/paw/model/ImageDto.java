package ar.edu.itba.paw.model;

public class ImageDto {
    // TODO: esta bien que esta clase este en models?
    private String mimeType;
    private byte[] data;

    public ImageDto(byte[] data, String mimeType) {
        this.data = data;
        this.mimeType = mimeType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
