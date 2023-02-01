package com.ayushsingh.bloggingapplication.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
