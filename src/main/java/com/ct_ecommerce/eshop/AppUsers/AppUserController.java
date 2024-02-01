package com.ct_ecommerce.eshop.AppUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for all requests for users
 * @Variable AppUserService ** service layer for users.
 * @RequestMapping("/api/user") ** route prefix
 */
@RestController
@RequestMapping("/api/user")
public class AppUserController {
    private final AppUserService AppUserService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public AppUserController(AppUserService AppUserService){
        this.AppUserService = AppUserService;
    }

    /**
     * Create new user
     * @PostMapping ** = post request.
     */
    @PostMapping(path = "/register")
    public void store(@RequestBody AppUser registrationUser){
        //create new user object
        AppUser newUser = new AppUser(registrationUser.getUsername(), registrationUser.getPassword(), registrationUser.getEmail(), registrationUser.getFirstName(), registrationUser.getLastName());

        //call service to save
        AppUserService.addNewUser(newUser);
    }

    /**
     * Log in existing user
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     */
    @PostMapping(path = "/login")
    public String loginUser(@RequestBody AppUserLoginBody appUserLoginBody){
        String jwt = AppUserService.loginUser(appUserLoginBody);
        if(jwt == null){
            return "Errorr in logiinnn";
        }else{
            AppUserLoginResponse response = new AppUserLoginResponse();
            response.setJwt(jwt);
            return "login success !!!!!";
        }
    }
}
