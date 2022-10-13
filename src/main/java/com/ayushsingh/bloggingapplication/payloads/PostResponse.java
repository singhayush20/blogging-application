package com.ayushsingh.bloggingapplication.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    
    private int pageNumber;
    private long totalElements;
    private long totalPages;
    private int pageSize;
    private boolean lastPage;
    private long currentPageSize;
    private List<PostDto> content;
    


    
}
