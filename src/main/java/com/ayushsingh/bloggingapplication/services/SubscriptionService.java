package com.ayushsingh.bloggingapplication.services;
import java.util.List;

import com.ayushsingh.bloggingapplication.payloads.CategoryDto2;
import com.ayushsingh.bloggingapplication.payloads.UserDto3;
public interface SubscriptionService {
    

    UserDto3 subscribeToCategory(int userid, int categoryid);

    UserDto3 unsubscribeFromCategory(int userid, int categoryid);

    List<CategoryDto2> getListOfSubscribedCategories(int userid);
}
