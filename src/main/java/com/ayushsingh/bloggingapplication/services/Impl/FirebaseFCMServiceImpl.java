package com.ayushsingh.bloggingapplication.services.Impl;

import com.ayushsingh.bloggingapplication.model.TargetNotification;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidFcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.firebase.messaging.AndroidConfig.Priority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FirebaseFCMServiceImpl {

    // public void sendNotificationToTarget(TargetNotification notification){
    // Message message=Message.builder().setAndroidConfig(null)
    // }
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    String defaultToken = "fOplegJHRw2j2RXwEAvMuB:APA91bG1v_IzGreT2IA4B7hZLhVmH1oOGy0zvkXbfP9SJQLn1rFGwy1lgw48TthvbL1H-TUR-pRV6zOgZGCESzvM77nRdP92EbYjKk3-c1GDxqdtrG-IXUjmVa6W2WwcBaSP9CDI1v7N";

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

    public void sendMessageToTopic(Integer categoryid) {
        String topic = "TOPIC" + categoryid;
        AndroidFcmOptions androidFcmOptions = AndroidFcmOptions.builder().setAnalyticsLabel("AnalyticsLabel").build();
        // See documentation on defining a message payload.
        Notification notification=Notification.builder().setTitle("SFBlog").setBody("Here is a new article for you").build();
        Message message = Message.builder()
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setPriority(Priority.HIGH)
                                .setFcmOptions(androidFcmOptions)
                                .build())
                .putData("Categoryid", categoryid.toString())
                .putData("Description", "A new article has been added!")
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
}
