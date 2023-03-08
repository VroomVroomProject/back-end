package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import com.backend.vroomvroom.service.board.IPostCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/board")
public class BoardController {

    private final IPostCategoryService postCategoryService;

    /**
     * 게시판 카테고리 생성
     * @param postCategoryRequestDto 이름, 순서, url 정보를 받는다.
     * @return 생성한 결과 정보를 반환
     */
    @PostMapping("/post-category")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCategoryResponseDto createPostCategory(@Valid @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        log.info("postCategory register, request : {}", postCategoryRequestDto);
        log.info("postCategory register, userId : {}", postCategoryRequestDto.getUserId());
        return postCategoryService.createPostCategory(postCategoryRequestDto);
    }


    /**
     * 게시판 카테고리 리스트 반환
     */
    @GetMapping("/post-category")
    @ResponseStatus(HttpStatus.OK)
    public List<PostCategoryResponseDto> listPostCategory() {
        return postCategoryService.listPostCategory();
    }
}
