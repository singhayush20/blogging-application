package com.ayushsingh.bloggingapplication.entities;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

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
    
    private String imgName;

    private Date addDate;
    //every post is associated with a category
    //many posts in one category
    @ManyToOne
    @JoinColumn(name="categoryID",nullable = false)
    private Category category;
    //every post is made by an user
    //many posts for one user
    @ManyToOne
    private User user;

    
}
