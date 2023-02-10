package com.ayushsingh.bloggingapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicNotification {
    
    private String topic;

    private String title;

    private String message;
}
