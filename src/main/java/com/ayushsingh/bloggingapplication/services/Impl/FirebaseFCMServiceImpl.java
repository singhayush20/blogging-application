package com.ayushsingh.bloggingapplication.services.Impl;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidFcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.firebase.messaging.AndroidConfig.Priority;

import java.util.List;
import com.ayushsingh.bloggingapplication.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ayushsingh.bloggingapplication.entities.Category;
import java.util.ArrayList;
import java.util.Set;

@Service
public class FirebaseFCMServiceImpl {

    // public void sendNotificationToTarget(TargetNotification notification){
    // Message message=Message.builder().setAndroidConfig(null)
    // }
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    // subscribe to topic
    public TopicManagementResponse subscribeUserToTopic(String userToken, int categoryid)
            throws FirebaseMessagingException {

        List<String> userTokens = new ArrayList<>();
        userTokens.add(userToken);
        String topic = "TOPIC" + categoryid;
        TopicManagementResponse response = firebaseMessaging.subscribeToTopic(userTokens, topic);
        System.out.println("Subscription failure count: " + response.getFailureCount() + " " + response.getErrors());
        return response;
    }

    public TopicManagementResponse unsubscribeUserFromTopic(String userToken, int categoryid)
            throws FirebaseMessagingException {
        List<String> userTokens = new ArrayList<>();
        userTokens.add(userToken);
        String topic = "TOPIC" + categoryid;
        TopicManagementResponse response = firebaseMessaging.unsubscribeFromTopic(userTokens, topic);
        System.out.println("Cancel Subscription failure count: " + response.getFailureCount());

        return response;
    }

    public void sendMessageToTopic(Integer categoryid, Post newPost, String notificationTitle) {
        String topic = "TOPIC" + categoryid;
        AndroidFcmOptions androidFcmOptions = AndroidFcmOptions.builder().setAnalyticsLabel("AnalyticsLabel").build();
        // See documentation on defining a message payload.
        Notification notification = Notification.builder().setTitle(notificationTitle).setBody(newPost.getTitle())
                .build();
        Message message = Message.builder()
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setPriority(Priority.HIGH)
                                .setFcmOptions(androidFcmOptions)
                                .build())
                .putData("categoryid", categoryid.toString())
                .putData("title", newPost.getTitle())
                .putData("content", newPost.getContent())
                .putData("image", newPost.getImage())
                .putData("postid", newPost.getPostId().toString())
                .putData("addDate", newPost.getAddDate().toString())
                .setTopic(topic)
                .setNotification(notification)
                .build();

        // Send a message to the devices subscribed to the provided topic.
        String response = "";
        try {
            response = firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            System.out.println("Sending message error");
            e.printStackTrace();
        }
        // Response is a message ID string.
        System.out.println("Send message to " + topic + " response: " + response);
    }

    // When user logs in subscribe to receive notifications
    public void subscribeToTopics(String userToken, Set<Category> subscribedCategories) {
        List<String> userTokens = new ArrayList<>();
        userTokens.add(userToken);

        for (Category category : subscribedCategories) {
            String topic = "TOPIC" + category.getCategoryId();
            try {
                firebaseMessaging.subscribeToTopic(userTokens, topic);
            } catch (FirebaseMessagingException e) {
                System.out.println("Error occured while subscribing to topic: " + topic);
                e.printStackTrace();
            }

        }

    }

    // When user logs out, unsubscribe
    public void unsubscribeFromTopics(String userToken, Set<Category> subscribedCategories) {
        List<String> userTokens = new ArrayList<>();
        userTokens.add(userToken);
        for (Category category : subscribedCategories) {
            String topic = "TOPIC" + category.getCategoryId();
            try {
                firebaseMessaging.unsubscribeFromTopic(userTokens, topic);
            } catch (FirebaseMessagingException e) {
                System.out.println("Error occurrred while unsubscribing");
                e.printStackTrace();
            }
        }

    }
}
