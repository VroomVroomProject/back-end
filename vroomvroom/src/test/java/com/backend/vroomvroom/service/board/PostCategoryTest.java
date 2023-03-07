package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.PostCategoryCreateDto;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PostCategoryTest {

    @Autowired
    private IPostCategoryRepository iPostCategoryRepository;

    @Autowired
    private IPostCategoryService iPostCategoryService;

    @Test
    public void createPostCategoryTest_게시판_카테고리_성공() {
        //given
        PostCategoryCreateDto postCategoryCreateDto = new PostCategoryCreateDto();
        postCategoryCreateDto.setName("Q&A");
        postCategoryCreateDto.setOrders(1);
        postCategoryCreateDto.setAdminWriteYn("N");
        postCategoryCreateDto.setUrl("/board/QandA");

        //when
        PostCategoryCreateDto savePostCategoryDto = iPostCategoryService.createPostCategory(postCategoryCreateDto);

        //then
        Assertions.assertThat(savePostCategoryDto).isNotNull();
        Assertions.assertThat(postCategoryCreateDto.getName()).isEqualTo(savePostCategoryDto.getName());
    }

}
