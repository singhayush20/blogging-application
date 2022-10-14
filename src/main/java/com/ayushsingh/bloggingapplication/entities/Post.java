package com.ayushsingh.bloggingapplication.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer postId;
    @Column(name="post_title",length=100,nullable=false)
    private String title;
    @Column(name="post_content",length=10000,nullable =false)
    private String content;
    @Column(name="image_name")
    private String image;
    @Column(name="post_date")
    private Date addDate;
    //every post is associated with a category
    //many posts in one category
    @ManyToOne
    @JoinColumn(name="categoryID",nullable = false)
    private Category category;
    //every post is made by an user
    //many posts for one user
    @ManyToOne
    @JoinColumn(name="userID",nullable = false)
    private User user;

    
}
