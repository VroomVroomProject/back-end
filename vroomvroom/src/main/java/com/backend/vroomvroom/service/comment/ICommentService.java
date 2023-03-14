package com.backend.vroomvroom.service.comment;

import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.dto.comment.response.CommentResponseDto;

import java.util.List;

public interface ICommentService {

    public CommentResponseDto commentRegister(CommentRequestDto commentRequestDto);

    public List<CommentResponseDto> getCommentAll(Long postId);

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto);

    public void deleteComment(Long commentId);
}
