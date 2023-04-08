package com.backend.vroomvroom.dto.board.request;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
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
public class PostRequestDto {

    @NotNull
    private Long postCategoryId;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String noticeYn;

    public static PostEntity mapToEntity(PostRequestDto postRequestDto, PostCategoryEntity postCategoryEntity, UserEntity userEntity) {
        return PostEntity.builder()
                .postCategory(postCategoryEntity)
                .user(userEntity)
                .title(postRequestDto.getTitle())
                .contents(postRequestDto.getContents())
                .commentCount(0)
                .views(0)
                .noticeYn(postRequestDto.getNoticeYn())
                .useYn("Y")
                .createUserId(userEntity.getId())
                .build();
    }

}
