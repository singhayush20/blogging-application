package com.ayushsingh.bloggingapplication.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class Role {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    //auto increment is not required- only a limited number of roles
    private int id;

    private String name;

}
