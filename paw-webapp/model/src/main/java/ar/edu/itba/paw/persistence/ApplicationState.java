package ar.edu.itba.paw.persistence;

public enum ApplicationState {
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    PENDING("Pending");

    private String state;

    ApplicationState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
