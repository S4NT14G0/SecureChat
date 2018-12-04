package com.santiago.securechat.data.entity;

public class SecureMessage {

    private final String secureMessage;

    public SecureMessage (final String secureMessage) {
        this.secureMessage = secureMessage;
    }

    public String getMessage() {
        return secureMessage;
    }
}
