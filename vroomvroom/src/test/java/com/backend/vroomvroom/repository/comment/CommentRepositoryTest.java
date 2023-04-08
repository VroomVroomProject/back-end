package com.backend.vroomvroom.repository.comment;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.comment.CommentEntity;
import com.backend.vroomvroom.entity.user.UserEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import com.backend.vroomvroom.repository.board.IPostRepository;
import com.backend.vroomvroom.repository.user.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    private IPostCategoryRepository iPostCategoryRepository;

    @Autowired
    private IPostRepository iPostRepository;

    @Autowired
    private ICommentRepository iCommentRepository;

    @Autowired
    private IUserRepository iUserRepository;

    private PostEntity savePost;
    private UserEntity userEntity;

    @BeforeEach
    @Rollback(value = false)
    public void setup() {
        userEntity = iUserRepository.findByLoginId("test1234").orElse(null);

        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("전체");
        postCategoryRequestDto.setUrlName("all");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/all");
        postCategoryRequestDto.setUseYn("Y");
        PostCategoryEntity saveCategory = iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setPostCategoryId(saveCategory.getId());
        postRequestDto.setTitle("게시물 제목1");
        postRequestDto.setContents("게시물 내용입니다.");
        postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부
        savePost = iPostRepository.save(PostRequestDto.mapToEntity(postRequestDto, saveCategory, userEntity));
    }

    @Test
    public void findByPostEntityAndUseYnOrderByCreateDateTime_테스트() throws InterruptedException {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");

        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        Thread.sleep(1000);

        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다2222");
        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        Thread.sleep(1000);

        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다3333");
        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        //when
        List<CommentEntity> commentList = iCommentRepository.findByPostAndUseYnOrderByCreateDateTime(savePost, "Y");

        //then
        assertThat(commentList.size()).isEqualTo(3);
    }

    @Test
    @Rollback(value = false)
    public void bulkUseYnSetting_댓글_일괄_삭제_테스트() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");

        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다2222");
        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다3333");
        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        //when
        int bulkCount = iCommentRepository.bulkUseYnSetting("N", savePost.getId());

        //then
        assertThat(bulkCount).isEqualTo(3);
    }
}
