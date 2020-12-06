package org.senla.share.entity;

public class JwtToken {
    private String jwtToken;

    public JwtToken() {
    }

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
