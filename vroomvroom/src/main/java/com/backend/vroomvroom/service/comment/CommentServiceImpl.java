package com.backend.vroomvroom.service.comment;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.config.security.user.UserDto;
import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.dto.comment.response.CommentResponseDto;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.comment.CommentEntity;
import com.backend.vroomvroom.entity.user.UserEntity;
import com.backend.vroomvroom.repository.board.IPostRepository;
import com.backend.vroomvroom.repository.comment.ICommentRepository;
import com.backend.vroomvroom.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.backend.vroomvroom.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final IPostRepository iPostRepository;
    private final ICommentRepository iCommentRepository;
    private final IUserRepository iUserRepository;

    @Transactional
    @Override
    public CommentResponseDto commentRegister(CommentRequestDto commentRequestDto, UserDto userDto) {

        PostEntity findPost = iPostRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> {
                    log.error("postId가 존재하지 않습니다. postId : {}", commentRequestDto.getPostId());
                    throw new CommonException(NOT_FOUND_ENTITY, "게시물을 찾을 수 없습니다. postId : " + commentRequestDto.getPostId());
                });

        UserEntity findUser = iUserRepository.findByLoginId(userDto.getLoginId()).orElse(null);

        CommentEntity comment = CommentRequestDto.mapToEntity(commentRequestDto, findPost, findUser);
        CommentEntity saveComment = iCommentRepository.save(comment);

        return CommentResponseDto.mapToDto(saveComment);
    }

    @Transactional
    @Override
    public List<CommentResponseDto> getCommentAll(Long postId) {

        PostEntity findPost = iPostRepository.findById(postId)
                .orElseThrow(() -> {
                    log.error("postId가 존재하지 않습니다. postId : {}", postId);
                    throw new CommonException(NOT_FOUND_ENTITY, "게시물을 찾을 수 없습니다. postId : " + postId);
                });

        List<CommentEntity> commentList = iCommentRepository.findByPostAndUseYnOrderByCreateDateTime(findPost, "Y");

        return commentList.stream().map(CommentResponseDto :: mapToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        // TODO: 2023-03-14 댓글 삭제는 작성한 사람만이 삭제가 가능하며, 관리자 또한 다른 사람의 댓글은 수정할 수 없다.
        // TODO: user 구현 이후에 본인이 쓴 댓글인지 아닌지 판별하는 로직 구현할 것

        CommentEntity findComment = iCommentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("commentId 존재하지 않습니다. commentId : {}", commentId);
                    throw new CommonException(NOT_FOUND_ENTITY, "댓글을 찾을 수 없습니다. commentId : " + commentId);
                });


        if(!(findComment.getPost().getId() == commentRequestDto.getPostId())) {
            log.error("등록되어 있는 댓글의 게시물 아이디와 수정하고자 하는 댓글의 게시물 아이디가 일치하지 않습니다. postId : {}", commentRequestDto.getPostId());
            throw new CommonException(INVALID_REQUEST, "등록되어 있는 댓글의 게시물 아이디와 수정하고자 하는 댓글의 게시물 아이디가 일치하지 않습니다. " + " postId " + commentRequestDto.getPostId());
        }

        findComment.update(commentRequestDto);

        return CommentResponseDto.mapToDto(findComment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        CommentEntity findComment = iCommentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("commentId 존재하지 않습니다. commentId : {}", commentId);
                    throw new CommonException(NOT_FOUND_ENTITY, "댓글을 찾을 수 없습니다. commentId : " + commentId);
                });

        findComment.delete();
    }
}
