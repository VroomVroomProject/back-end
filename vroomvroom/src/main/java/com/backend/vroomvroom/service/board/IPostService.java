package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.config.security.user.UserDto;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostPageResponse;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;
import org.springframework.data.domain.Pageable;


public interface IPostService {

    public PostResponseDto postRegister(PostRequestDto postRequestDto, UserDto user);

    public PostPageResponse getPostAll(Pageable pageable, String urlName, String type, String keyword);

    public PostResponseDto getPostDetail(String urlName, Long postId);

    public Long updatePost(Long postId, PostRequestDto postRequestDto);

    public void deletePost(Long postId);
}
