/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.data.entity;

/**
 * SecureMessage just wraps a String to force the Room database to use our
 * custom TypeConverter so we can encrypt the String before storing it
 * and decrypt the string back into this object upon retrieval from db.
 */
public class SecureMessage {

    private final String secureMessage;

    public SecureMessage (final String secureMessage) {
        this.secureMessage = secureMessage;
    }

    public String getMessage() {
        return secureMessage;
    }
}
