package com.santiago.securechat.data.entity.type_converter;

import android.arch.persistence.room.TypeConverter;

import com.santiago.securechat.data.entity.SecureMessage;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Converts Secure Messages to encrypted string before storage into database.
 * Encrypted message is decrypted on retrieval from database.
 * Encryption Algorithm: PBEWithMD5AndDES
 */
public class SecureMessageTypeConverter {
    private final static String ENCRYPTION_PASSWORD = "SECURECH@T";

    @TypeConverter
    public SecureMessage fromEncryptedMessage (String encryptedMessage) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(ENCRYPTION_PASSWORD);
        String decryptedMessage = basicTextEncryptor.decrypt(encryptedMessage);
        return new SecureMessage(decryptedMessage);
    }

    @TypeConverter
    public String toEncryptedHash (SecureMessage secureMessage) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(ENCRYPTION_PASSWORD);
        String encryptedMessage = basicTextEncryptor.encrypt(secureMessage.getMessage());

        return encryptedMessage;
    }

}
