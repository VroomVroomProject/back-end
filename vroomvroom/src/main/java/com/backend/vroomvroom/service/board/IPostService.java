package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;

public interface IPostService {

    public PostResponseDto postRegister(PostRequestDto postRequestDto);

}
