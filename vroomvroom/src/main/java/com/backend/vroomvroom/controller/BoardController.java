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
     * @param postCategoryRequestDto
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
     * 게시판 카테고리 수정
     */
    @PutMapping("/post-category/{postCategoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updatePostCategory(
            @PathVariable("postCategoryId") Long postCategoryId,
            @Valid @RequestBody PostCategoryRequestDto postCategoryRequestDto
    ) {
        log.info("postCategory updateById, postCategoryId : {}", postCategoryId);
        return postCategoryService.updatePostCategory(postCategoryId, postCategoryRequestDto);
    }

    /**
     * 게시판 카테고리 삭제
     */
    @DeleteMapping("/post-category/{postCategoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostCategory(@PathVariable("postCategoryId") Long postCategoryId) {
        log.info("postCategory deleteById, postCategoryId : {}", postCategoryId);
        postCategoryService.deletePostCategory(postCategoryId);
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

    /**
     * 게시물 페이징 조회
     */
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

    /**
     * 게시물 상세보기
     */
    @GetMapping("/post/{urlName}/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto getPostDetail(
            @PathVariable("urlName") String urlName,
            @PathVariable("postId") Long postId
    ) {
        return postService.getPostDetail(urlName, postId);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostRequestDto postRequestDto
    ) {
        log.info("post updateById, postId : {}", postId);
        return postService.updatePost(postId, postRequestDto);
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("postId") Long postId) {
        log.info("post deleteById, postId : {}", postId);
        postService.deletePost(postId);
    }


}
