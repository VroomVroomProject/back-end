package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;

import java.util.List;

public interface IPostCategoryService {

    public PostCategoryResponseDto createPostCategory(PostCategoryRequestDto postCategoryRequestDto);

    public List<PostCategoryResponseDto> listPostCategory();
}
