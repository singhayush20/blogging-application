package com.ayushsingh.bloggingapplication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ayushsingh.bloggingapplication.security.CustomUserDetailsService;
import com.ayushsingh.bloggingapplication.security.JwtAuthenticationFilter;



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
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests() // authorize http requests
                .anyRequest() // authorize all requests
                .authenticated() // authenticate
                .and()
                .exceptionHandling()//for jwt authentication (not required with basic authentication)
                .authenticationEntryPoint(this.authenticationEntryPoint)//for jwt authentication (not required with basic authentication)
                /*These two lines will ensure that if ever any error occurs due to authentication unauthorization
                 * the method in entry point is invoked
                 */
                .and()//for jwt authentication (not required with basic authentication)
                .sessionManagement()//for jwt authentication (not required with basic authentication)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//for jwt authentication (not required with basic authentication)
                // .httpBasic(); // type of authentication - basic

            http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);//for jwt authentication (not required with basic authentication)

        // using this method, we will not get a html/css web form, instead
        // we will now get the javascript generated form.
    }
    

    

    //for jwt authentication (not required with basic authentication)
    //authentication manager will be required to authenticate password
    //while logging in
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        
        return super.authenticationManagerBean();
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