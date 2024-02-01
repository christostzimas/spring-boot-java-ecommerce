package com.ct_ecommerce.eshop.AppUsers;

import com.ct_ecommerce.eshop.securityServices.EncoderService;
import com.ct_ecommerce.eshop.securityServices.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service layer for users
 * @Variable AppUserRepository ** repository layer for users.
 * @Variable encoderService ** for password encryption
 */
@Service
public class AppUserService {
    private final AppUserRepository AppUserRepository;
    private EncoderService EncoderService;
    private JWTService JWTService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param appUserRepository ** used for db actions
     * @param encoderService ** used for passwords
     */
    @Autowired
    public AppUserService(AppUserRepository appUserRepository, EncoderService encoderService, JWTService jwtService) {
        AppUserRepository = appUserRepository;
        EncoderService = encoderService;
        JWTService = jwtService;
    }
    /**
     * Save new user
     * @param user ** user for save
     * @Errors ** IllegalStateException if user exists + general
     */
    @Transactional
    public void addNewUser(AppUser user) {
        try{
            /** check if user already exists by email or username which are unique */
            Optional<AppUser> findUserByEmail = AppUserRepository.findUserByEmail(user.getEmail());
            Optional<AppUser> findUserByUsername = AppUserRepository.findUserByEmail(user.getUsername());

            if(findUserByEmail.isPresent() || findUserByUsername.isPresent()){
                throw new IllegalStateException("User already exists");
            }
            /** encrypt password */
            user.setPassword(EncoderService.encryptPassword(user.getPassword()));

            /** Save new user */
            AppUserRepository.save(user);
        }catch(IllegalStateException ex){
            throw new IllegalArgumentException("User already exists --update");
        }catch(Exception ex){
            throw new RuntimeException("Error creating the user", ex);
        }
    }

    /**
     * Logs in a user and provides an authentication token back.
     * @param loginBody The login request.
     * @return The authentication token. Null if the request was invalid.
     */
    public String loginUser(AppUserLoginBody loginBody) {
        /**  Check if user exists. */
        Optional<AppUser> dbUser = AppUserRepository.findUserByUsername(loginBody.getUsername());
        if (dbUser.isPresent()) {
            /**  Issue token if password is correct. */
            AppUser user = dbUser.get();
            if (EncoderService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return JWTService.generateJWT(user);
            }
        }
        return null;
    }
}
