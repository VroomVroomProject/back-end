package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import com.backend.vroomvroom.dto.board.response.PostPageResponse;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;
import com.backend.vroomvroom.service.board.IPostCategoryService;
import com.backend.vroomvroom.service.board.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final IPostService postService;

    /**
     * 게시판 카테고리 생성
     * @param postCategoryRequestDto 이름, 순서, url 정보를 받는다.
     * @return 생성된 결과 정보를 반환
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
    public List<PostCategoryResponseDto> listPostCategory(@RequestParam("useYn") String useYn) {
        return postCategoryService.listPostCategory(useYn);
    }


    /**
     * 게시물 작성
     * @param postRequestDto
     * @return PostResponseDto 생성된 게시물 정보를 반환
     */
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto postRegister(@Valid @RequestBody PostRequestDto postRequestDto) {
        log.info("post register, request : {}", postRequestDto);
        log.info("post register, userId : {}", postRequestDto.getUserId());
        
        return postService.postRegister(postRequestDto);
    }

    @GetMapping("/post/{urlName}")
    @ResponseStatus(HttpStatus.OK)
    public PostPageResponse getAllPage(
            @PathVariable("urlName") String urlName,
            @PageableDefault(page = 0, size = 10, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "keyword", required = false) String keyword
            ) {
        log.info("post getAllPage type : {}, keyword : {}", type, keyword);
        return postService.getPostAll(pageable, urlName, type, keyword);
    }


}
