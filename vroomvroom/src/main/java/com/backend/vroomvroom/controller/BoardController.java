package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.board.PostCategoryCreateDto;
import com.backend.vroomvroom.service.board.IPostCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/board")
public class BoardController {

    private final IPostCategoryService postCategoryService;

    @PostMapping("/post-category/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCategoryCreateDto createPostCategory(@Valid @RequestBody PostCategoryCreateDto postCategoryCreateDto) {
        return postCategoryService.createPostCategory(postCategoryCreateDto);
    }
}
