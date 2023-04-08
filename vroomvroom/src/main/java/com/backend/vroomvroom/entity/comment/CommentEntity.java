package com.backend.vroomvroom.entity.comment;

import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.entity.BaseEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.user.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tb_comment")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Lob
    private String contents;

    public CommentEntity(Long id, PostEntity post, UserEntity user, String contents, String useYn) {
        super(useYn);
        this.id = id;
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
        // TODO: 2023-03-14 user 기능 구현 후에 update 시 updateUserId 세팅
    }
}
