package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class PostCategoryServiceTest {

    @Autowired
    private IPostCategoryService iPostCategoryService;

    @Test
    @Rollback(value = false)
    public void createPostCategoryTest_게시판_카테고리_생성_성공() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setName("Q&A");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA");

        //when
        PostCategoryResponseDto savePostCategoryDto = iPostCategoryService.createPostCategory(postCategoryRequestDto);

        //then
        assertThat(savePostCategoryDto).isNotNull();
        assertThat(postCategoryRequestDto.getName()).isEqualTo(savePostCategoryDto.getName());
    }

    @Test
    @Rollback(value = false)
    public void listPostCategory_게시판_카테고리_리스트_반환() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setName("Q&A");
        postCategoryRequestDto.setOrders(2);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/QandA");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);

        postCategoryRequestDto.setName("지식");
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/knowledge");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);

        postCategoryRequestDto.setName("공지사항");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/notice");
        iPostCategoryService.createPostCategory(postCategoryRequestDto);

        //when
        List<PostCategoryResponseDto> postCategoryResponseDtos = iPostCategoryService.listPostCategory();

        for (PostCategoryResponseDto postCategoryResponseDto : postCategoryResponseDtos) {
            log.info(postCategoryResponseDto.toString());
        }
        //then
    }

}
