package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.PostCategoryCreateDto;

public interface IPostCategoryService {
    public PostCategoryCreateDto createPostCategory(PostCategoryCreateDto postCategoryCreateDto);
}
