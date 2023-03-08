package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    private PostCategoryResponseDto postCategory;

    @BeforeEach
    public void setup() {
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setName("Q&A");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA");

       postCategory = iPostCategoryService.createPostCategory(postCategoryRequestDto);
    }

    @Test
    public void postRegister_게시판_생성_성공() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setUserId(1L);
        postRequestDto.setPostCategoryId(postCategory.getPostCategoryId());
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

}
