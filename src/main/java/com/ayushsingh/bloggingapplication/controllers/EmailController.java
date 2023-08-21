package com.ayushsingh.bloggingapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
import com.ayushsingh.bloggingapplication.repositories.UserRep;
import com.ayushsingh.bloggingapplication.services.EmailService;
import com.ayushsingh.bloggingapplication.services.OtpService;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/blog/auth")
public class EmailController {

        @Autowired
        private EmailService emailService;

        @Autowired
        private OtpService otpService;

        @Autowired
        private UserRep userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        // Send OTP for email verification
        @PostMapping("/sendotp")
        public ResponseEntity<SuccessResponse<String>> sendOTP(@RequestParam("email") String email/*
                                                                                                   * ,
                                                                                                   * HttpSession session
                                                                                                   */) {

                System.out.println("Sending OTP for email id: " + email);

                // generate the otp
                int otp = this.otpService.generateOTP(email);
                String subject = "Email Verification OTP";
                String message = "" +
                                "<div style='border:1px solid #e2e2e2; padding:20px'>"
                                + "<h2>"
                                + "Your email verification OTP is "
                                + "<b>"
                                + otp
                                + "</b>"
                                + "</h2>"
                                + "</div>";

                boolean result = this.emailService.sendEmail(subject, message, email);

                return (result)
                                ? new ResponseEntity<>(
                                                new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                                                                AppConstants.SUCCESS_MESSAGE, "OTP sent successfully"),
                                                HttpStatus.OK)
                                : new ResponseEntity<>(new SuccessResponse<>(AppConstants.FAILURE_CODE,
                                                AppConstants.FAILURE_MESSAGE, "Otp not sent due to some problem!"),
                                                HttpStatus.OK);
        }

        // Verify OTP for email verification
        @PostMapping("/verify-otp")
        public ResponseEntity<ApiResponse> verifyOtp(@RequestParam("otp") int otp,
                        @RequestParam("email") String email) {
                boolean flag = false;

                int storedOTP = this.otpService.getOTP(email);
                System.out.println("Stored OTP: " + storedOTP + " sent otp: " + otp);
                if (storedOTP == otp) {
                        System.out.println("OTP is verified");
                        flag = true;
                }
                ApiResponse apiResponse = (flag)
                                ? new ApiResponse("Email verified successfully!", AppConstants.SUCCESS_CODE,
                                                AppConstants.SUCCESS_MESSAGE)
                                : new ApiResponse("Email verification failed!", AppConstants.FAILURE_CODE,
                                                AppConstants.FAILURE_MESSAGE);
                return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        }

        // Send OTP for password reset
        @PostMapping("/reset-password-otp")
        public ResponseEntity<ApiResponse> resetPasswordOTP(@RequestParam("email") String email) {
                boolean result = false;
                System.out.println("Sending OTP on email id: " + email);
                Optional<User> user = userRepository.findByEmail(email);
                if (user.isPresent()) {

                        int otp = this.otpService.generateOTP(email);
                        String subject = "Reset Password OTP";
                        String message = "" +
                                        "<div style='border:1px solid #e2e2e2; padding:20px'>"
                                        + "<h2>"
                                        + "Your verification OTP is "
                                        + "<b>"
                                        + otp
                                        + "</b>"
                                        + "</h2>"
                                        + "</div>";

                        result = this.emailService.sendEmail(subject, message, email);
                } else {
                        throw new ResourceNotFoundException("User", email, 0);
                }
                return (result)
                                ? new ResponseEntity<ApiResponse>(
                                                new ApiResponse("Otp sent successfully on email",
                                                                AppConstants.SUCCESS_CODE,
                                                                AppConstants.SUCCESS_MESSAGE),
                                                HttpStatus.OK)
                                : new ResponseEntity<ApiResponse>(
                                                new ApiResponse("Otp not sent due to some problem!",
                                                                AppConstants.FAILURE_CODE,
                                                                AppConstants.FAILURE_MESSAGE),
                                                HttpStatus.OK);
        }

        // Reset passoword, send the email id, new password and the otp
        @PostMapping("/reset-password")
        public ResponseEntity<ApiResponse> resetPasswordOTP(@RequestParam("otp") int otp,
                        @RequestParam("email") String email, @RequestParam("password") String password,
                        HttpSession session) {
                boolean flag = false;

                int storedOTP = this.otpService.getOTP(email);

                if (storedOTP == otp) {
                        System.out.println("Otp is verified");
                        User user = userRepository.findByEmail(email).get();
                        System.out.println("User found: " + user.getEmail() + " " + user.getId());
                        String encryptedPassword = passwordEncoder.encode(password);
                        System.out.println("New encrypted password: " + encryptedPassword);
                        user.setPassword(encryptedPassword);
                        userRepository.save(user);
                        flag = true;
                }
                ApiResponse apiResponse = (flag)
                                ? new ApiResponse("Your password has been reset!", AppConstants.SUCCESS_CODE,
                                                AppConstants.SUCCESS_MESSAGE)
                                : new ApiResponse("Password Reset failed!", AppConstants.FAILURE_CODE,
                                                AppConstants.FAILURE_MESSAGE);
                return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        }

}
