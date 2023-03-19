package com.backend.vroomvroom.dto.board.request;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
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
    private Long userId;

    @NotNull
    private Long postCategoryId;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String noticeYn;

    public static PostEntity mapToEntity(PostRequestDto postRequestDto, PostCategoryEntity postCategoryEntity /**User user**/) {
        return PostEntity.builder()
//                .user(user)
                .postCategory(postCategoryEntity)
                .title(postRequestDto.getTitle())
                .contents(postRequestDto.getContents())
                .commentCount(0)
                .views(0)
                .noticeYn(postRequestDto.getNoticeYn())
                .useYn("Y")
                .createUserId(postRequestDto.getUserId())
                .build();
    }

}
