package com.ayushsingh.bloggingapplication.services;



public interface EmailService{
    public boolean sendEmail(String subject, String message, String to);
    
}