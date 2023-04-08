package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.common.annotation.CurrentUser;
import com.backend.vroomvroom.config.security.user.UserDto;
import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.dto.comment.response.CommentResponseDto;
import com.backend.vroomvroom.service.comment.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/board")
public class CommentController {

    private final ICommentService commentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto commentRegister(
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @CurrentUser UserDto user
    ) {
        log.info("comment register, request : {}", commentRequestDto);
        log.info("comment register, userId : {}", user.getUserId());

        return commentService.commentRegister(commentRequestDto, user);
    }

    @GetMapping("/post/{postId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentAll(@PathVariable("postId") Long postId) {
        return commentService.getCommentAll(postId);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment(
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {
        log.info("comment updateById, commentId : {}", commentId);
        return commentService.updateComment(commentId, commentRequestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        log.info("comment deleteById, commentId : {}", commentId);
        commentService.deleteComment(commentId);
    }

}
