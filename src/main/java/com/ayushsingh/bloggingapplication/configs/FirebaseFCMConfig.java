package com.ayushsingh.bloggingapplication.configs;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FirebaseFCMConfig{

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app;
        if(FirebaseApp.getApps().isEmpty()) {
            app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
        }else {
            app = FirebaseApp.initializeApp(firebaseOptions);
        }
        return FirebaseMessaging.getInstance(app);
    }

}
