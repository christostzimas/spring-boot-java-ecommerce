package com.ct_ecommerce.eshop.securityServices;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Service for handling password encryption and match.
 */
@Service
public class EncoderService {
    /**
     *  How many salt rounds should the encryption run.
     *  @Value annotation points to application.properties file
     */
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    /** The salt built after construction. */
    private String salt;

    /**
     * Post construction method.
     * Post constructor executes after dependency injection
     */
    @PostConstruct
    public void postConstruct() {
        salt = BCrypt.gensalt(saltRounds);
    }

    /**
     * Encrypts the given password.
     * @param password plain text password.
     * @return encrypted password.
     */
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Verifies that a password is correct.
     * @param password plain text password provided by the user.
     * @param hash encrypted password saved.
     * @return True if the password is correct, false otherwise.
     */
    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
