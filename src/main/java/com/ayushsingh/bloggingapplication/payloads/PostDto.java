package com.ayushsingh.bloggingapplication.payloads;



import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;



// @NoArgsConstructor
// @Getter
// @Setter
public class PostDto {
    
    public PostDto() {
    }

    private int postId;

    private String title;
    

    private String content;

    private String addDate;

    private String image;
  
    private UserDto2 user;

    private CategoryDto2 category;

    //this will get the comments also when the post is fetched
    //separate api for the comments is not required in this case
    private Set<CommentDto> comments=new HashSet<>();

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public UserDto2 getUser() {
        return user;
    }

    public void setUser(UserDto2 user) {
        this.user = user;
    }
    public CategoryDto2 getCategory() {
        return category;
    }

    public void setCategory(CategoryDto2 category) {
        this.category = category;
    }
    @JsonIgnore
    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }


}
