package com.backend.vroomvroom.entity.comment;

import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.entity.BaseEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
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

    // TODO: 2023-03-07 회원기능 추가 후에 작업할 것
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Lob
    private String contents;

    public CommentEntity(Long id, PostEntity post, String contents, String useYn) {
        super(useYn);
        this.id = id;
        this.post = post;
        this.contents = contents;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
        // TODO: 2023-03-14 user 기능 구현 후에 update 시 updateUserId 세팅
    }
}
