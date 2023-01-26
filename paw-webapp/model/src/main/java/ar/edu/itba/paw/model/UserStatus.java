package ar.edu.itba.paw.model;

public enum UserStatus {
    VERIFIED("VERIFIED"),
    NOT_VERIFIED("NOT_VERIFIED");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
