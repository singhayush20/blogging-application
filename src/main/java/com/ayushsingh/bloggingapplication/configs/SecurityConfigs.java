package com.ayushsingh.bloggingapplication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.ayushsingh.bloggingapplication.security.CustomUserDetailsService;



/**
 * SecurityConfigs
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests() // authorize http requests
                .anyRequest() // authorize all requests
                .authenticated() // authenticate
                .and()
                .httpBasic(); // type of authentication - basic

        // using this method, we will not get a html/css web form, instead
        // we will now get the javascript generated form.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // for authentication, our customUserDetailService will
        // be called, it will search for the user and then
        // if the user is found, it will be returned else exception will be thrown

        auth.userDetailsService(this.customUserDetailsService)
                .passwordEncoder(this.passwordEncoder);

        // password stored in the backend will be encrypted
        // using BCryptPasswordEncoder
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}

// class SecurityConfig {
//     @Autowired
//     private CustomUserDetailService customUserDetailService;
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.csrf().disable()
//                 .authorizeHttpRequests() // authorize http requests
//                 .anyRequest() // authorize all requests
//                 .authenticated() // authenticate
//                 .and()
//                 .httpBasic(); // type of authentication - basic

//         return http.build();
//     }
   
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }