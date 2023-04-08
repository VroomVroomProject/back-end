package com.backend.vroomvroom.dto.comment.request;

import com.backend.vroomvroom.entity.comment.CommentEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CommentRequestDto {

    @NotNull
    private Long postId;

    @NotBlank
    private String contents;

    public static CommentEntity mapToEntity(CommentRequestDto commentRequestDto, PostEntity postEntity, UserEntity userEntity) {
        return CommentEntity.builder()
                .post(postEntity)
                .user(userEntity)
                .contents(commentRequestDto.getContents())
                .createUserId(userEntity.getId())
                .useYn("Y")
                .build();
    }

}
