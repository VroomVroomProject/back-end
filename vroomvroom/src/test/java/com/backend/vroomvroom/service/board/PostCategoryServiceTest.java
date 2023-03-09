package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import lombok.extern.slf4j.Slf4j;
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
public class PostCategoryServiceTest {

    @Autowired
    private IPostCategoryService iPostCategoryService;

    @Test
    public void createPostCategoryTest_게시판_카테고리_생성_성공() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA");
        postCategoryRequestDto.setUseYn("Y");

        //when
        PostCategoryResponseDto savePostCategoryDto = iPostCategoryService.createPostCategory(postCategoryRequestDto);

        //then
        assertThat(savePostCategoryDto).isNotNull();
        assertThat(postCategoryRequestDto.getViewName()).isEqualTo(savePostCategoryDto.getViewName());
    }

    @Test
    public void createPostCategoryTest_게시판_카테고리_생성_실패() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);
        
        //when
        postCategoryRequestDto.setViewName("지식");
        postCategoryRequestDto.setUrlName("questions"); //동일한 urlName인 경우
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/knowledge");
        postCategoryRequestDto.setUseYn("Y");

        assertThatThrownBy(() -> iPostCategoryService.createPostCategory(postCategoryRequestDto))
                .isInstanceOf(RuntimeException.class);

        postCategoryRequestDto.setViewName("지식");
        postCategoryRequestDto.setUrlName("questions1");
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA"); //동일한 url인 경우
        postCategoryRequestDto.setUseYn("Y");

        assertThatThrownBy(() -> iPostCategoryService.createPostCategory(postCategoryRequestDto))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void listPostCategory_게시판_카테고리_리스트_반환() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(2);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);

        postCategoryRequestDto.setViewName("지식");
        postCategoryRequestDto.setUrlName("knowledge");
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/knowledge");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);

        postCategoryRequestDto.setViewName("공지사항");
        postCategoryRequestDto.setUrlName("notice");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/notice");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);

        //when
        List<PostCategoryResponseDto> postCategoryResponseDtos = iPostCategoryService.listPostCategory("Y");

        for (PostCategoryResponseDto postCategoryResponseDto : postCategoryResponseDtos) {
            log.info(postCategoryResponseDto.toString());
        }
        //then
        assertThat(postCategoryResponseDtos.size()).isEqualTo(3);
    }

}
