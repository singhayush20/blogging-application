package com.ayushsingh.bloggingapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.APIException;
import com.ayushsingh.bloggingapplication.payloads.JWTAuthRequest;
import com.ayushsingh.bloggingapplication.payloads.JWTAuthResponse;
import com.ayushsingh.bloggingapplication.payloads.UserDto;
import com.ayushsingh.bloggingapplication.security.JwtTokenHelper;
import com.ayushsingh.bloggingapplication.services.UserService;
import com.ayushsingh.bloggingapplication.services.Impl.FirebaseFCMServiceImpl;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
@RestController
@RequestMapping("/blog/auth/")
public class AuthController {
    // to generate the token
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    // user details
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FirebaseFCMServiceImpl fcmService;

    @Autowired
    private UserService userService;


    // authentication manager will be used to authenticate the
    // password
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JWTAuthResponse>> createToken(
        @RequestBody JWTAuthRequest request,
        @RequestParam(name="devicetoken") String deviceToken
    ) {
    
            this.authenticate(request.getUsername(),request.getPassword());
   
        //If authentication is successfull, generate the token
        User userDetails=(User) this.userDetailsService.loadUserByUsername(request.getUsername());
        String generatedToken=this.jwtTokenHelper.generateToken(userDetails);
        //subscribe the device token for fcm 
        fcmService.subscribeToTopics(deviceToken, userDetails.getCategories());
        //send this token in the response
        JWTAuthResponse response=new JWTAuthResponse();
        response.setToken(generatedToken);
        response.setUserId(userDetails.getId());
        SuccessResponse<JWTAuthResponse> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,response);
        return new  ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    private void authenticate(String username, String password) {

        // Create authentication token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try{
            // this can throw excptions which will be handled globally 
        //in cases like user is disabled
        this.authenticationManager.authenticate(authenticationToken);
        }
        catch(DisabledException e){
            throw new APIException("user is disabled");
        }
        catch(LockedException e){           
            throw new APIException("User is locked");
        }
        catch(BadCredentialsException e){
            throw new APIException("Invalid username or password");
        }
    }

    //To unsubscribe from notifications
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
        @RequestParam(name="userid") Integer userid,
        @RequestParam(name="devicetoken") String deviceToken
    ) throws Exception{
        userService.logoutUser(userid, deviceToken);
        ApiResponse response=new ApiResponse("Logged Out Successfully",AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //Register new user api
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserDto>> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser=this.userService.registerNewUser(userDto);
        SuccessResponse<UserDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,registeredUser);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

}
