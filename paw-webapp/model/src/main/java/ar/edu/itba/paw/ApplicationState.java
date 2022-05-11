package ar.edu.itba.paw;

public enum ApplicationState {
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    PENDING("PENDING"),
    ALL("ALL");

    private final String  state;

    ApplicationState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }


}
