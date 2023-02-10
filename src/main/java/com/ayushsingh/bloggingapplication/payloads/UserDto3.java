package com.ayushsingh.bloggingapplication.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
@Data
@NoArgsConstructor
public class UserDto3 {
    
    private int id;

    private String firstName;

    private String lastName;

    private String about;

    private Set<CategoryDto2> categories;
}
