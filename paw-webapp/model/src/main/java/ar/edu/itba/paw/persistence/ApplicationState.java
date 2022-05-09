package ar.edu.itba.paw.persistence;

public enum ApplicationState {
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    PENDING("PENDING");

    private String state;

    ApplicationState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }


}
