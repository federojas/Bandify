package ar.edu.itba.paw;

public enum ApplicationState {
    ALL("ALL"),
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    private final String  state;

    ApplicationState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }


}
