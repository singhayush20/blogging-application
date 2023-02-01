package com.ayushsingh.bloggingapplication.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor // whenever we have to create user object we can use this
@Getter
@Setter
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY can also be used, AUTO takes more processing
                                                    // for IDENTITY, a default value for Id has to be set, else
                                                    // exception is thrown
    private int id;
    @Column(name = "username", nullable = false, length = 100,unique=true)
    private String username;

    @Column(name="firstname",nullable=false,length=50)
    private String firstName;

    @Column(name="lastname",length=50)
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(name="description")
    private String about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL/*Save role when user is saved*/, fetch = FetchType.EAGER) // each user can have many roles
    @JoinTable(name = "user_role", // the name of the table which manages this relationship
            // specify the join columns in that table
            joinColumns = @JoinColumn(
                    /*
                     * name of the join column
                     * The name of the foreign key column which stores the user id
                     * The table in which it is found depends upon the context.
                     */
                    name = "user",
                    /*
                     * The name of the primary key in the
                     * user table which works as the foreign key in this table
                     * column.
                     */
                    referencedColumnName = "id"),
            /*
             * The foreign key columns of the join table which reference the primary table
             * of the entity that does not own the association. (I.e. the inverse side of
             * the association).
             */
            inverseJoinColumns = @JoinColumn(
                    /*
                     * The name of the foreign key column. The table in which it is found depends
                     * upon the context. The column which keeps the role id in this table
                     */
                    name = "role",

                    /*
                     * The name of the column referenced by this foreign key column. The primary key
                     * in the role table working as the foreign key in this table
                     */
                    referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
       
       List<SimpleGrantedAuthority> authorities=this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
}

@Override
public String getUsername() {
        
        //return the email
        return this.email;
}

@Override
public boolean isAccountNonExpired() {
        return true;
}

@Override
public boolean isAccountNonLocked() {
        return true;
}

@Override
public boolean isCredentialsNonExpired() {
        return true;
}

@Override
public boolean isEnabled() {
        //user is true by default
        return true;
}
}
