package com.ayushsingh.bloggingapplication.services.Impl;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;


import com.google.common.cache.LoadingCache;
import com.ayushsingh.bloggingapplication.services.OtpService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Service
public class OtpServiceImpl implements OtpService {
    private static final Integer EXPIRE_MINS = 5;
    private LoadingCache<String, Integer> otpCache;

    public OtpServiceImpl() {
        super();
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public Integer load(String key) {
                                return 0;
                            }
                        });
    }

    @Override
    public int generateOTP(String email) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        System.out.println("Saving generated otp for id: "+email+" otp: "+otp);
        otpCache.put(email, otp);
        return otp;
    }

    @Override
    public int getOTP(String email) {
        try {
            int otp= otpCache.get(email);
            System.out.println("Saved otp retrieved: "+otp+" email: "+email);
            return otp;
        } catch (Exception e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void clearOTP(String email) {
        otpCache.invalidate(email);
    }

}
