package com.ayushsingh.bloggingapplication.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer categoryId;
    @Column(name="title",length=50,nullable = false,unique = true)
    private String categoryName;
    @Column(name="description",nullable = false)
    private String categoryDescription;

    @OneToMany(mappedBy="category",cascade = CascadeType.ALL,fetch = FetchType.LAZY) 
    //one category can have many posts
    //this list is mapped by the category member of the post (mapping on category)
    //cascade = CascadeType.ALL: This ensures that the posts are removed if the category is removed
    //Defines the set of cascadable operations that are propagated to the 
    //associated entity. The value cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}
    private List<Post> posts=new ArrayList<>();
}
