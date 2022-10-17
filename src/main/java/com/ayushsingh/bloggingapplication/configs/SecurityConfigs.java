package com.ayushsingh.bloggingapplication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ayushsingh.bloggingapplication.security.CustomUserDetailsService;
import com.ayushsingh.bloggingapplication.security.JwtAuthenticationFilter;

/**
 * SecurityConfigs
 */
// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true) //this will allow to apply
// security on each and every
// //method based on roles
// public class SecurityConfigs extends WebSecurityConfigurerAdapter {
// @Autowired
// private CustomUserDetailsService customUserDetailsService;

// @Autowired
// PasswordEncoder passwordEncoder;
// @Autowired
// private AuthenticationEntryPoint authenticationEntryPoint;
// @Autowired
// private JwtAuthenticationFilter jwtAuthenticationFilter;
// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http.csrf().disable()
// .authorizeHttpRequests() // authorize http requests
// .antMatchers("/api/v1/auth/login") //make the authentication url public
// .permitAll() //permit these urls to be accessed directly
// .antMatchers(HttpMethod.GET)
// .permitAll() //allow all the get APIs to be accessible without login
// .anyRequest() // authorize all requests
// .authenticated() // authenticate
// .and()
// .exceptionHandling()//for jwt authentication (not required with basic
// authentication)
// .authenticationEntryPoint(this.authenticationEntryPoint)//for jwt
// authentication (not required with basic authentication)
// /*These two lines will ensure that if ever any error occurs due to
// authentication unauthorization
// * the method in entry point is invoked
// */
// .and()//for jwt authentication (not required with basic authentication)
// .sessionManagement()//for jwt authentication (not required with basic
// authentication)
// .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//for jwt
// authentication (not required with basic authentication)
// // .httpBasic(); // type of authentication - basic

// http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);//for
// jwt authentication (not required with basic authentication)

// // using this method, we will not get a html/css web form, instead
// // we will now get the javascript generated form.
// }

// for jwt authentication (not required with basic authentication)
// authentication manager will be required to authenticate password
// while logging in
// @Bean
// @Override
// public AuthenticationManager authenticationManagerBean() throws Exception {

// return super.authenticationManagerBean();
// }

// @Override
// protected void configure(AuthenticationManagerBuilder auth) throws Exception
// {
// // for authentication, our customUserDetailService will
// // be called, it will search for the user and then
// // if the user is found, it will be returned else exception will be thrown

// auth.userDetailsService(this.customUserDetailsService)
// .passwordEncoder(this.passwordEncoder);

// // password stored in the backend will be encrypted
// // using BCryptPasswordEncoder
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }
// }

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigs {
    

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests() // authorize http requests
                .antMatchers("/api/v1/auth/*") // make the authentication url public
                .permitAll() // permit these urls to be accessed directly
                .antMatchers(HttpMethod.GET)
                .permitAll() // allow all the get APIs to be accessible without login
                .anyRequest() // authorize all requests
                .authenticated() // authenticate
                .and()
                .exceptionHandling()// for jwt authentication (not required with basic authentication)
                .authenticationEntryPoint(this.authenticationEntryPoint)// for jwt authentication (not required with
                                                                        // basic authentication)
                /*
                 * These two lines will ensure that if ever any error occurs due to
                 * authentication unauthorization
                 * the method in entry point is invoked
                 */
                .and()// for jwt authentication (not required with basic authentication)
                .sessionManagement()// for jwt authentication (not required with basic authentication)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);// for jwt authentication (not required with
                                                                        // basic authentication)
        // .httpBasic(); // type of authentication - basic

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// for jwt
                                                                                                       // authentication
                                                                                                       // (not required
                                                                                                       // with basic
                                                                                                       // authentication)

        // using this method, we will not get a html/css web form, instead
        // we will now get the javascript generated form.

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(this.passwordEncoder);
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}