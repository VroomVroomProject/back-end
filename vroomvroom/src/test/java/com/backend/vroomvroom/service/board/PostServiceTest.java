package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import com.backend.vroomvroom.dto.board.response.PostPageResponse;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private IPostService iPostService;

    @Autowired
    private IPostCategoryService iPostCategoryService;

    private PostCategoryResponseDto postCategoryAll;
    private PostCategoryResponseDto postCategoryQuestions;
    private PostCategoryResponseDto postCategoryNotice;

    @BeforeEach
    public void setup() {
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("전체");
        postCategoryRequestDto.setUrlName("all");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/all");
        postCategoryRequestDto.setUseYn("Y");

        postCategoryAll = iPostCategoryService.createPostCategory(postCategoryRequestDto);

        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(2);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions");
        postCategoryRequestDto.setUseYn("Y");

        postCategoryQuestions = iPostCategoryService.createPostCategory(postCategoryRequestDto);

        postCategoryRequestDto.setViewName("공지사항");
        postCategoryRequestDto.setUrlName("notice");
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("Y");
        postCategoryRequestDto.setUrl("/board/notice");
        postCategoryRequestDto.setUseYn("Y");

        postCategoryNotice = iPostCategoryService.createPostCategory(postCategoryRequestDto);
    }

    @Test
    public void postRegister_게시판_생성_성공() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setUserId(1L);
        postRequestDto.setPostCategoryId(postCategoryQuestions.getPostCategoryId());
        postRequestDto.setTitle("게시판 제목1");
        postRequestDto.setContents("게시판 내용입니다.");
        postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부

        //when
        PostResponseDto result = iPostService.postRegister(postRequestDto);
        log.info(result.toString());

        //then
        assertThat(result).isNotNull();
        assertThat(postRequestDto.getTitle()).isEqualTo(result.getTitle());
        assertThat(postRequestDto.getContents()).isEqualTo(result.getContents());
        assertThat(postRequestDto.getPostCategoryId()).isEqualTo(result.getPostCategoryId());
    }

    /**
     * 존재하지 않는 postCategoryId 값인 경우 예외 반환
     */
    @Test
    public void postRegister_게시판_생성_실패() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setUserId(1L);
        postRequestDto.setPostCategoryId(-1L);
        postRequestDto.setTitle("게시판 제목1");
        postRequestDto.setContents("게시판 내용입니다.");
        postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부

        //when
        assertThatThrownBy(() -> iPostService.postRegister(postRequestDto))
                .isInstanceOf(RuntimeException.class);

    }

    /**
     * 검색 조건 X
     */
    @Test
    public void getPostAll_게시판_페이징_조회_성공() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setUserId(1L);
        for(int i = 0; i < 10; i++) {
            postRequestDto.setPostCategoryId(postCategoryAll.getPostCategoryId());
            postRequestDto.setTitle(postCategoryAll.getViewName() + i);
            postRequestDto.setContents(postCategoryAll.getViewName() + " 내용 " + i);
            postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부
            iPostService.postRegister(postRequestDto);

            postRequestDto.setPostCategoryId(postCategoryQuestions.getPostCategoryId());
            postRequestDto.setTitle(postCategoryQuestions.getViewName() + i);
            postRequestDto.setContents(postCategoryQuestions.getViewName() + " 내용 " + i);
            postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부
            iPostService.postRegister(postRequestDto);

            postRequestDto.setPostCategoryId(postCategoryNotice.getPostCategoryId());
            postRequestDto.setTitle(postCategoryNotice.getViewName() + i);
            postRequestDto.setContents(postCategoryNotice.getViewName() + " 내용 " + i);
            postRequestDto.setNoticeYn("Y"); //공지사항 게시물 여부
            iPostService.postRegister(postRequestDto);
        }

        Pageable pageable = PageRequest.of(0, 5);

        //when
        PostPageResponse postAll = iPostService.getPostAll(pageable, postCategoryAll.getUrlName(), "", "");

        PostPageResponse postQuestions = iPostService.getPostAll(pageable, postCategoryQuestions.getUrlName(), "", "");

        PostPageResponse postNotice = iPostService.getPostAll(pageable, postCategoryNotice.getUrlName(), "", "");

        //then
        assertThat(postAll.getPostList().size()).isEqualTo(5);
        assertThat(postAll.getTotalElements()).isEqualTo(20); //전체 게시글 수(10) + Q&A 게시글 수(10)
        assertThat(postAll.getTotalPages()).isEqualTo(4);

        assertThat(postQuestions.getPostList().size()).isEqualTo(5);
        assertThat(postQuestions.getTotalElements()).isEqualTo(10);
        assertThat(postQuestions.getTotalPages()).isEqualTo(2);

        assertThat(postNotice.getPostList().size()).isEqualTo(5);
        assertThat(postNotice.getTotalElements()).isEqualTo(10);
        assertThat(postNotice.getTotalPages()).isEqualTo(2);
    }

    /**
     * 검색 조건 O
     */
    @Test
    public void getPostAll_게시판_페이징_조회_검색조건_성공() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setUserId(1L);
        for(int i = 0; i < 10; i++) {
            postRequestDto.setPostCategoryId(postCategoryAll.getPostCategoryId());
            postRequestDto.setTitle(postCategoryAll.getViewName() + i);
            postRequestDto.setContents(postCategoryAll.getViewName() + " 내용 " + i);
            postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부
            iPostService.postRegister(postRequestDto);

            postRequestDto.setPostCategoryId(postCategoryQuestions.getPostCategoryId());
            postRequestDto.setTitle(postCategoryQuestions.getViewName() + i);
            postRequestDto.setContents(postCategoryQuestions.getViewName() + " 내용 " + i);
            postRequestDto.setNoticeYn("N"); //공지사항 게시물 여부
            iPostService.postRegister(postRequestDto);

            postRequestDto.setPostCategoryId(postCategoryNotice.getPostCategoryId());
            postRequestDto.setTitle(postCategoryNotice.getViewName() + i);
            postRequestDto.setContents(postCategoryNotice.getViewName() + " 내용 " + i);
            postRequestDto.setNoticeYn("Y"); //공지사항 게시물 여부
            iPostService.postRegister(postRequestDto);
        }


        Pageable pageable = PageRequest.of(0, 5);

        //when
        PostPageResponse postAll = iPostService.getPostAll(pageable, postCategoryAll.getUrlName(), "title", "");
        PostPageResponse postAllTitleKeyword = iPostService.getPostAll(pageable, postCategoryAll.getUrlName(), "title", "전체");
        PostPageResponse postAllContentsKeyword = iPostService.getPostAll(pageable, postCategoryAll.getUrlName(), "contents", "전체");
        PostPageResponse postAllContentsKeywordNotice = iPostService.getPostAll(pageable, postCategoryAll.getUrlName(), "contents", "공지");


        //then
        assertThat(postAll.getPostList().size()).isEqualTo(5);
        assertThat(postAll.getTotalElements()).isEqualTo(20); //전체 게시글 수(10) + Q&A 게시글 수(10)
        assertThat(postAll.getTotalPages()).isEqualTo(4);

        assertThat(postAllTitleKeyword.getPostList().size()).isEqualTo(5);
        assertThat(postAllTitleKeyword.getTotalElements()).isEqualTo(10); //"전체" 가 들어간 제목의 게시물 수
        assertThat(postAllTitleKeyword.getTotalPages()).isEqualTo(2);

        assertThat(postAllContentsKeyword.getPostList().size()).isEqualTo(5);
        assertThat(postAllContentsKeyword.getTotalElements()).isEqualTo(10); //"전체" 가 들어간 내용의 게시물 수
        assertThat(postAllContentsKeyword.getTotalPages()).isEqualTo(2);

        //전체 게시판에서 공지 내용이 들어간 게시물 검색
        assertThat(postAllContentsKeywordNotice.getPostList().size()).isEqualTo(0);
        assertThat(postAllContentsKeywordNotice.getTotalElements()).isEqualTo(0);
        assertThat(postAllContentsKeywordNotice.getTotalPages()).isEqualTo(0);

    }

}
