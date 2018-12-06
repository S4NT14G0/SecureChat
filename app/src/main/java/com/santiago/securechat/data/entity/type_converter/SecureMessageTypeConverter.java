/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.data.entity.type_converter;

import android.arch.persistence.room.TypeConverter;

import com.santiago.securechat.data.entity.SecureMessage;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Converts Secure Messages to encrypted string before storage into database.
 * Encrypted message is decrypted on retrieval from database.
 * Encryption Algorithm: PBEWithMD5AndDES
 *
 */
public class SecureMessageTypeConverter {
    // Password for encryption
    private final static String ENCRYPTION_PASSWORD = "SECURECH@T";


    /**
     * Convert encrypted string into a decrypted SecureMessage
     * @param encryptedMessage - String to decrypt
     * @return - SecureMessage containing decrypted string
     */
    @TypeConverter
    public SecureMessage fromEncryptedMessage (String encryptedMessage) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(ENCRYPTION_PASSWORD);
        String decryptedMessage = basicTextEncryptor.decrypt(encryptedMessage);
        return new SecureMessage(decryptedMessage);
    }

    /**
     * Convert SecureMessage object into an encrypted string for storage in database
     * @param secureMessage - Message to encrypt.
     * @return - Encrypted message String
     */
    @TypeConverter
    public String toEncryptedHash (SecureMessage secureMessage) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(ENCRYPTION_PASSWORD);
        String encryptedMessage = basicTextEncryptor.encrypt(secureMessage.getMessage());

        return encryptedMessage;
    }

}
