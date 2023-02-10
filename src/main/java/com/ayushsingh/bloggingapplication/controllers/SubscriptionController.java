package com.ayushsingh.bloggingapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.payloads.CategoryDto2;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
import com.ayushsingh.bloggingapplication.payloads.UserDto3;
import com.ayushsingh.bloggingapplication.services.SubscriptionService;
@RestController
@RequestMapping("/blog/topics")
public class SubscriptionController {
   
    @Autowired
    private SubscriptionService subscriptionService;

    @PutMapping("/subscribe")
    public ResponseEntity<SuccessResponse<UserDto3>> subscribeToCategory(@RequestParam(name="userid") int userid, @RequestParam(name="categoryid") int categoryid){
        UserDto3 user=subscriptionService.subscribeToCategory(userid, categoryid);
        SuccessResponse<UserDto3> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,user);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<SuccessResponse<UserDto3>> unsubscribeFromCategory(@RequestParam(name="userid") int userid, @RequestParam(name="categoryid") int categoryid){
        UserDto3 user=subscriptionService.unsubscribeFromCategory(userid, categoryid);
        SuccessResponse<UserDto3> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,user);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/subscribed")
    public ResponseEntity<SuccessResponse<List<CategoryDto2>>> getSubscribedCategories(@RequestParam(name="userid") int userid){
        List<CategoryDto2> categories=subscriptionService.getListOfSubscribedCategories(userid);
        SuccessResponse<List<CategoryDto2>> list=new SuccessResponse<>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE,categories);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
