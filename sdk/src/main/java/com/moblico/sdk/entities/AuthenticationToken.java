package com.moblico.sdk.entities;

import java.util.Date;

public class AuthenticationToken {
    private final String token;
    private final Date tokenExpiry;

    private AuthenticationToken(final String token, final Date expiry) {
        this.token = token;
        tokenExpiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public boolean isValid() {
        if( tokenExpiry == null ) {
            return false;
        }
        final Date now = new Date();
        return now.before(tokenExpiry);
    }
}
