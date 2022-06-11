package ar.edu.itba.paw.model;

public enum MembershipState {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    private final String  state;

    MembershipState(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
