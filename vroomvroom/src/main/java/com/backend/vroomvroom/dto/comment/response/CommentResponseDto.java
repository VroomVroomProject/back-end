package com.backend.vroomvroom.dto.comment.response;

import com.backend.vroomvroom.entity.comment.CommentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentResponseDto {

    private Long id;

    private String userNickname;

    private String contents;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

    @Builder
    public CommentResponseDto(Long id, String userNickname, String contents, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.id = id;
        this.userNickname = userNickname;
        this.contents = contents;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

    public static CommentResponseDto mapToDto(CommentEntity commentEntity) {
        return CommentResponseDto.builder()
                .id(commentEntity.getId())
                .userNickname("하드코딩")
                .contents(commentEntity.getContents())
                .createDateTime(commentEntity.getCreateDateTime())
                .updateDateTime(commentEntity.getUpdateDateTime())
                .build();
    }

}
