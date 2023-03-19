package com.backend.vroomvroom.entity.board;

import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tb_post")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategoryEntity postCategory;

    // TODO: 2023-03-07 회원기능 추가 후에 작업할 것
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    private String title;

    @Lob
    private String contents;

    private int views;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "notice_yn")
    private String noticeYn;

    public PostEntity(Long id, PostCategoryEntity postCategory, String title, String contents, int views, int commentCount, String noticeYn, String useYn) {
        super(useYn);
        this.id = id;
        this.postCategory = postCategory;
        this.title = title;
        this.contents = contents;
        this.views = views;
        this.commentCount = commentCount;
        this.noticeYn = noticeYn;
    }


    public void update(PostRequestDto postRequestDto, PostCategoryEntity postCategory) {
        this.postCategory = postCategory;
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.noticeYn = postRequestDto.getNoticeYn();
        // TODO: 2023-03-13 user 기능 구현 후에 update 시 updateUserId 세팅
    }
}

