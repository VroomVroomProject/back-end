package com.backend.vroomvroom.service.comment;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.comment.request.CommentRequestDto;
import com.backend.vroomvroom.dto.comment.response.CommentResponseDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.comment.CommentEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import com.backend.vroomvroom.repository.board.IPostRepository;
import com.backend.vroomvroom.repository.comment.ICommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    private IPostCategoryRepository iPostCategoryRepository;

    @Autowired
    private IPostRepository iPostRepository;

    @Autowired
    private ICommentRepository iCommentRepository;

    @Autowired
    private ICommentService iCommentService;

    private PostEntity savePost;

    @BeforeEach
    public void setup() {
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("전체");
        postCategoryRequestDto.setUrlName("all");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/all");
        postCategoryRequestDto.setUseYn("Y");
        PostCategoryEntity saveCategory = iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setUserId(1L);
        postRequestDto.setPostCategoryId(saveCategory.getId());
        postRequestDto.setTitle("게시물 제목1");
        postRequestDto.setContents("게시물 내용입니다.");
        postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부
        savePost = iPostRepository.save(PostRequestDto.mapToEntity(postRequestDto, saveCategory));
    }

    @Test
    public void commentRegister_댓글_등록_성공() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");

        //when
        CommentResponseDto saveComment = iCommentService.commentRegister(commentRequestDto);

        //then
        assertThat(commentRequestDto.getContents()).isEqualTo(saveComment.getContents());

    }

    /**
     * 게시물 아이디가 존재하지 않는 경우 예외를 반환해야 한다.
     */
    @Test
    public void commentRegister_댓글_등록_실패() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(-1L); //존재하지 않는 게시물 아이디
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");

        //when
        assertThatThrownBy(() ->
            iCommentService.commentRegister(commentRequestDto)
        ).isInstanceOf(RuntimeException.class);

    }


    @Test
    public void getCommentAll_댓글_리스트_가져오기_성공() {
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
        List<CommentResponseDto> commentList = iCommentService.getCommentAll(savePost.getId());

        //then
        assertThat(commentList.size()).isEqualTo(3);
    }


    /**
     * 게시물 아이디가 존재하지 않는다면 예외를 반환해야 한다.
     */
    @Test
    public void getCommentAll_댓글_리스트_가져오기_실패() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");
        iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        //when
        assertThatThrownBy(() ->
                iCommentService.getCommentAll(-1L)
        ).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void updateComment_댓글_수정_성공() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");
        CommentEntity saveComment = iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        CommentRequestDto updateRequestDto = new CommentRequestDto();
        updateRequestDto.setUserId(1L);
        updateRequestDto.setPostId(savePost.getId());
        updateRequestDto.setContents("수정된 댓글입니다.");

        //when
        CommentResponseDto updatedComment = iCommentService.updateComment(saveComment.getId(), updateRequestDto);

        //then
        assertThat(commentRequestDto.getContents()).isNotEqualTo(updatedComment.getContents());

    }

    /**
     * 게시물 아이디가 다를 경우에 댓글 수정이 불가하고 예외를 반환해야 한다.
     */
    @Test
    public void updateComment_댓글_수정_실패() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");
        CommentEntity saveComment = iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        CommentRequestDto updateRequestDto = new CommentRequestDto();
        updateRequestDto.setUserId(1L);
        updateRequestDto.setPostId(-1L); //다른 게시물 아이디
        updateRequestDto.setContents("수정된 댓글입니다.");

        //when
        assertThatThrownBy(() ->
                iCommentService.updateComment(saveComment.getId(), updateRequestDto)
        ).isInstanceOf(RuntimeException.class);
        

    }

    @Test
    public void deleteComment_댓글_삭제_성공() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUserId(1L);
        commentRequestDto.setPostId(savePost.getId());
        commentRequestDto.setContents("댓글 입니다. 댓글 입니다.");
        CommentEntity saveComment = iCommentRepository.save(CommentRequestDto.mapToEntity(commentRequestDto, savePost));

        //when
        iCommentService.deleteComment(saveComment.getId());

        //then
        assertThat(saveComment.getUseYn()).isEqualTo("N");

    }
}
