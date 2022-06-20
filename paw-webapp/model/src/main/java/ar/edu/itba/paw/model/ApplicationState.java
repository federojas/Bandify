package ar.edu.itba.paw.model;

public enum ApplicationState {
    ALL("ALL"),
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    SELECTED("SELECTED");

    private final String  state;

    ApplicationState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }


}
