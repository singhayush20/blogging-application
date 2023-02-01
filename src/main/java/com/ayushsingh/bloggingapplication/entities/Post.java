package com.ayushsingh.bloggingapplication.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;


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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer postId;
    @Column(name="post_title",nullable=false)
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
    @JoinColumn(name="categoryid",nullable = false)
    private Category category;
    //every post is made by an user
    //many posts for one user
    @ManyToOne
    @JoinColumn(name="userid",nullable = false)
    private User user;

    @OneToMany(mappedBy="post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> comments=new HashSet<>();

    
}
