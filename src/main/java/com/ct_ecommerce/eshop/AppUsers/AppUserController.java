package com.ct_ecommerce.eshop.AppUsers;

import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for all requests for users
 * @Variable AppUserService ** service layer for users.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/user") ** route prefix
 */
@RestController
@RequestMapping("/api/user")
public class AppUserController {
    private final AppUserService AppUserService;
    private final ResponseService ResponseService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public AppUserController(AppUserService AppUserService, ResponseService ResponseService){
        this.AppUserService = AppUserService;
        this.ResponseService = ResponseService;
    }

    /**
     * Create new user
     * @PostMapping ** = post request.
     */
    @PostMapping(path = "/register")
    public ResponseEntity store(@RequestBody AppUser registrationUser){
        try {
            //create new user object
            AppUser newUser = new AppUser(registrationUser.getUsername(), registrationUser.getPassword(), registrationUser.getEmail(), registrationUser.getFirstName(), registrationUser.getLastName());

            //call service to save
            AppUserService.addNewUser(newUser);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Log in existing user
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     */
    @PostMapping(path = "/login")
    public ResponseEntity loginUser(@RequestBody AppUserLoginBody appUserLoginBody){
        String jwt = AppUserService.loginUser(appUserLoginBody);
        if(jwt == null){
            return ResponseService.BadRequestResponse("Wrong credentials");
        }else{
            AppUserLoginResponse response = new AppUserLoginResponse();
            response.setJwt(jwt);
            return ResponseService.SuccessfullLogin(response);
        }
    }

    /**
     * Returns the profile of the currently logged-in user.
     * @param user The authentication principal object.
     * @return The user profile.
     */
    @GetMapping(path = "/profile")
    public AppUser getLoggedUserInfo(@AuthenticationPrincipal AppUser user){
        return user;
    }
}
