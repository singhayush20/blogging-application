package com.ayushsingh.bloggingapplication.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import lombok.Setter;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;

    @Column(name="description",length=200)
    private String content;

    @ManyToOne
    @JoinColumn(name = "postid")
    private Post post;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

}
