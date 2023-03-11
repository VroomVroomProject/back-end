package com.backend.vroomvroom.dto.board.response;

import com.backend.vroomvroom.entity.board.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PostResponseDto {

    private String userNickName;

    private Long postCategoryId;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

    private String title;

    private String contents;

    private String noticeYn;

    @Builder
    public PostResponseDto(String userNickName, Long postCategoryId, LocalDateTime createDateTime, LocalDateTime updateDateTime, String title, String contents, String noticeYn) {
        this.userNickName = userNickName;
        this.postCategoryId = postCategoryId;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.title = title;
        this.contents = contents;
        this.noticeYn = noticeYn;
    }

    @Builder


    public static PostResponseDto mapToDto (PostEntity postEntity) {
        return PostResponseDto.builder()
                .userNickName("하드코딩")
                .postCategoryId(postEntity.getPostCategory().getId())
                .createDateTime(postEntity.getCreateDateTime())
                .updateDateTime(postEntity.getUpdateDateTime())
                .title(postEntity.getTitle())
                .contents(postEntity.getContents())
                .noticeYn(postEntity.getNoticeYn())
                .build();
    }
}
