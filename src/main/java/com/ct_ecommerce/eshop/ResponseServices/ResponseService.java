package com.ct_ecommerce.eshop.ResponseServices;

import com.ct_ecommerce.eshop.AppUsers.AppUserLoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Value("${application.security.disabled}")
    private boolean debugMode;

    public boolean isDebugModeOn() {
        return debugMode;
    }

    public ResponseEntity SuccessResponse(){
        return ResponseEntity.ok().build();
    }

    public ResponseEntity SuccessResponseWithMsg(String msg){
        return ResponseEntity.ok().body(msg);
    }

    public ResponseEntity SuccessResponse(List list){
        return ResponseEntity.ok().body(list);
    }

    public ResponseEntity BadRequestResponse(String message){
        if(!this.debugMode){
            return genericServerError();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    public ResponseEntity SuccessfullLogin(AppUserLoginResponse response){
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity genericServerError(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal Server Error");
    }
}
