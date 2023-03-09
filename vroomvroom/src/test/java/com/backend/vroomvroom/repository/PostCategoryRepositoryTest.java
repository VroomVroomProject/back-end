package com.backend.vroomvroom.repository;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class PostCategoryRepositoryTest {

    @Autowired
    IPostCategoryRepository postCategoryRepository;

    private PostCategoryEntity postCategoryEntity;

    @BeforeEach
    public void setup() {
        postCategoryEntity = PostCategoryEntity.builder()
                .viewName("Q&A")
                .urlName("questions")
                .adminWriteYn("N")
                .orders(1)
                .url("/test")
                .useYn("Y")
                .build();
    }

    @Test
    public void existsById_아이디_존재_여부_성공() {

        //given
        PostCategoryEntity saveEntity = postCategoryRepository.save(postCategoryEntity);
        //when
        boolean checked = postCategoryRepository.existsById(saveEntity.getId());
        log.info("checked value : {}",checked);
        //then
        assertThat(checked).isTrue();
    }

    @Test
    public void existsById_아이디_존재_여부_실패() {

        //given
        PostCategoryEntity saveEntity = postCategoryRepository.save(postCategoryEntity);
        //when
        boolean checked = postCategoryRepository.existsById(-1L);
        log.info("checked value : {}",checked);
        //then
        assertThat(checked).isFalse();
    }

    /**
     * urlName만 동일한 경우
     */
    @Test
    public void existsByUrlnameOrUrl_UrlName_존재_여부_성공() {
        //given
        PostCategoryEntity saveEntity = postCategoryRepository.save(postCategoryEntity);

        //when
        boolean checked = postCategoryRepository.existsByUrlNameOrUrl("questions", "/abcdefg");
        log.info("checked value : {}",checked);

        //then
        assertThat(checked).isTrue();
    }

    /**
     * url만 동일한 경우
     */
    @Test
    public void existsByUrlnameOrUrl_Url_존재_여부_성공() {
        //given
        PostCategoryEntity saveEntity = postCategoryRepository.save(postCategoryEntity);

        //when
        boolean checked = postCategoryRepository.existsByUrlNameOrUrl("abcdefg", "/test");
        log.info("checked value : {}",checked);

        //then
        assertThat(checked).isTrue();
    }

    /**
     * urlName과 url 둘 다 동일한 경우
     */
    @Test
    public void existsByUrlnameOrUrl_존재_여부_성공() {
        //given
        PostCategoryEntity saveEntity = postCategoryRepository.save(postCategoryEntity);

        //when
        boolean checked = postCategoryRepository.existsByUrlNameOrUrl("question", "/test");
        log.info("checked value : {}",checked);

        //then
        assertThat(checked).isTrue();
    }

    /**
     * urlName과 url 둘 다 다른 경우
     */
    @Test
    public void existsByUrlnameOrUrl_존재_여부_실패() {
        //given
        PostCategoryEntity saveEntity = postCategoryRepository.save(postCategoryEntity);

        //when
        boolean checked = postCategoryRepository.existsByUrlNameOrUrl("question1", "/test1");
        log.info("checked value : {}",checked);

        //then
        assertThat(checked).isFalse();
    }
}

