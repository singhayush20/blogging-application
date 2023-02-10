package com.ayushsingh.bloggingapplication.services.Impl;

import java.util.List;
import java.util.Optional;

import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ayushsingh.bloggingapplication.entities.Category;
import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.payloads.CategoryDto2;
import com.ayushsingh.bloggingapplication.payloads.UserDto3;
import com.ayushsingh.bloggingapplication.repositories.CategoryRep;
import com.ayushsingh.bloggingapplication.repositories.UserRep;
import com.ayushsingh.bloggingapplication.services.SubscriptionService;
@Service
public class SubscriptionServiceImpl implements SubscriptionService{


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRep userRep;

    @Autowired
    CategoryRep categoryRep;

    @Override
    public UserDto3 subscribeToCategory(int userid, int categoryid) {
        Optional<User> user=userRep.findById(userid);
        Optional<Category> category=categoryRep.findById(categoryid);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User", "user id", userid);
        }
        else if(category.isEmpty()){
            throw new ResourceNotFoundException("category", "category id", categoryid);
        }

        User oldUser=user.get();
        oldUser.getCategories().add(category.get());
        User updatedUser=userRep.save(oldUser);
        return this.userToDto(updatedUser);
    }

        @Override
        public UserDto3 unsubscribeFromCategory(int userid, int categoryid){
            Optional<User> user=userRep.findById(userid);
            Optional<Category> category=categoryRep.findById(categoryid);
            if(user.isEmpty()){
                throw new ResourceNotFoundException("User", "user id", userid);
            }
            else if(category.isEmpty()){
                throw new ResourceNotFoundException("category", "category id", categoryid);
            }
            User oldUser=user.get();
            boolean result=oldUser.getCategories().remove(category.get());
            System.out.println("Is unsubscribed: "+result);
            User newUser=userRep.save(oldUser);
            return this.userToDto(newUser);
        }

        

    @Override
        public List<CategoryDto2> getListOfSubscribedCategories(int userid) {
            Optional<User> user=userRep.findById(userid);
            if(user.isEmpty()){
                throw new ResourceNotFoundException("User", "user id", userid);
            }
            Set<Category> subscribedCategories=user.get().getCategories();
            ArrayList<CategoryDto2> subscribedCat=new ArrayList<>();
            subscribedCategories.forEach((element)->{
                subscribedCat.add(this.modelMapper.map(element, CategoryDto2.class));
            });
            return subscribedCat;
        }

    private UserDto3 userToDto(User user){
        return this.modelMapper.map(user, UserDto3.class);
    }

   
    
}
