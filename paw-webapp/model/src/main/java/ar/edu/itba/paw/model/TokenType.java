package ar.edu.itba.paw.model;

public enum TokenType {
    VERIFY("verify"),
    RESET("reset");

    private String type;

    TokenType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
