package ar.edu.itba.paw;

public enum TokenType {
    VERIFY("verify"),
    RESET("reset");

    private final String type;

    TokenType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
