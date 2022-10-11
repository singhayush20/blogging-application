package com.ayushsingh.bloggingapplication.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor //whenever we have to create user object we can use this
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) //IDENTITY can also be used, AUTO takes more processing
                                                //for IDENTITY, a default value for Id has to be set, else exception is thrown
    private int id;
    @Column(name="user_name",nullable=false,length=100)
    private String name;
    private String email;
    private String password;
    private String about;
    
}
