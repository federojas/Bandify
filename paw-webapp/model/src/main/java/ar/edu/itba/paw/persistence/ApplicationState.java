package ar.edu.itba.paw.persistence;

public enum ApplicationState {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    ALL("ALL");

    private final String  state;

    ApplicationState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }


}
