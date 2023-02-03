package com.ayushsingh.bloggingapplication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ayushsingh.bloggingapplication.security.CustomUserDetailsService;
import com.ayushsingh.bloggingapplication.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc // for Swagger
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigs {
    public static final String[] PUBLIC_URLS = {
            "/blog/auth/**", // for authentication
            "/v3/api-docs", // to get list of all APIs
            "/v2/api-docs", // to use swagger UI
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
    };

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
                .requestMatchers(PUBLIC_URLS)
                .permitAll()
                // .antMatchers("/api/v1/auth/*") // make the authentication url public
                // .permitAll()
                // .antMatchers("/v3/api-docs")//for swagger api documentation
                // .permitAll()// permit these urls to be accessed directly
                .requestMatchers(HttpMethod.GET)
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
        http.authenticationProvider(daoAuthenticationProvider());
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

    @Bean
    public WebMvcConfigurer customConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.defaultContentType(MediaType.APPLICATION_JSON);
            }
        };
    }

}