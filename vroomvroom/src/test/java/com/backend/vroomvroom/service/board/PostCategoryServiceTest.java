package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
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
    private IPostCategoryRepository iPostCategoryRepository;

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
        postCategoryRequestDto.setUrl("/board/questions");
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
        postCategoryRequestDto.setUrl("/board/questions");
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
                .isInstanceOf(CommonException.class);

        postCategoryRequestDto.setViewName("지식");
        postCategoryRequestDto.setUrlName("questions1");
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions"); //동일한 url인 경우
        postCategoryRequestDto.setUseYn("Y");

        assertThatThrownBy(() -> iPostCategoryService.createPostCategory(postCategoryRequestDto))
                .isInstanceOf(CommonException.class);
    }

    @Test
    public void listPostCategory_게시판_카테고리_리스트_반환() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(2);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        postCategoryRequestDto.setViewName("지식");
        postCategoryRequestDto.setUrlName("knowledge");
        postCategoryRequestDto.setOrders(3);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/knowledge");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        postCategoryRequestDto.setViewName("공지사항");
        postCategoryRequestDto.setUrlName("notice");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/notice");
        postCategoryRequestDto.setUseYn("Y");
        iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        //when
        List<PostCategoryResponseDto> postCategoryResponseDtos = iPostCategoryService.listPostCategory("Y");

        for (PostCategoryResponseDto postCategoryResponseDto : postCategoryResponseDtos) {
            log.info(postCategoryResponseDto.toString());
        }
        //then
        assertThat(postCategoryResponseDtos.size()).isEqualTo(3);
    }

    @Test
    public void updatePostCategory_게시판_카테고리_수정_성공() {
        
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions");
        postCategoryRequestDto.setUseYn("Y");
        PostCategoryEntity savedCategoryEntity = iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));
        
        //업데이트 정보
        PostCategoryRequestDto updatePostCategoryRequestDto = new PostCategoryRequestDto();
        updatePostCategoryRequestDto.setViewName("Q&A수정");
        updatePostCategoryRequestDto.setUrlName("questions_2");
        updatePostCategoryRequestDto.setOrders(1);
        updatePostCategoryRequestDto.setAdminWriteYn("N");
        updatePostCategoryRequestDto.setUrl("/board/questions_2");
        updatePostCategoryRequestDto.setUseYn("Y");

        //when
        Long updatedId = iPostCategoryService.updatePostCategory(savedCategoryEntity.getId(), updatePostCategoryRequestDto);
        PostCategoryEntity updatePostCategoryEntity = iPostCategoryRepository.findById(updatedId).orElse(null);

        //then
        assertThat(savedCategoryEntity.getId()).isEqualTo(updatedId);
        assertThat(postCategoryRequestDto.getViewName()).isNotEqualTo(updatePostCategoryEntity.getViewName());
        assertThat(postCategoryRequestDto.getUrlName()).isNotEqualTo(updatePostCategoryEntity.getUrlName());
        assertThat(postCategoryRequestDto.getOrders()).isEqualTo(updatePostCategoryEntity.getOrders());
        assertThat(postCategoryRequestDto.getAdminWriteYn()).isEqualTo(updatePostCategoryEntity.getAdminWriteYn());
        assertThat(postCategoryRequestDto.getUrl()).isNotEqualTo(updatePostCategoryEntity.getUrl());
        
    }

    @Test
    public void updatePostCategory_게시판_카테고리_수정_실패() {

        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions");
        postCategoryRequestDto.setUseYn("Y");
        PostCategoryEntity savedCategoryEntity = iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        //업데이트 정보
        PostCategoryRequestDto updateEqualUrlNameDto = new PostCategoryRequestDto();
        updateEqualUrlNameDto.setViewName("Q&A수정");
        updateEqualUrlNameDto.setUrlName("questions"); //이미 존재하는 urlName으로 업데이트를 하려고 한다면?
        updateEqualUrlNameDto.setOrders(1);
        updateEqualUrlNameDto.setAdminWriteYn("N");
        updateEqualUrlNameDto.setUrl("/board/questions_2");
        updateEqualUrlNameDto.setUseYn("Y");

        PostCategoryRequestDto updateEqualUrlDto = new PostCategoryRequestDto();
        updateEqualUrlDto.setViewName("Q&A수정");
        updateEqualUrlDto.setUrlName("questions22222");
        updateEqualUrlDto.setOrders(1);
        updateEqualUrlDto.setAdminWriteYn("N");
        updateEqualUrlDto.setUrl("/board/questions"); //이미 존재하는 url로 업데이트를 하려고 한다면?
        updateEqualUrlDto.setUseYn("Y");

        PostCategoryRequestDto updateNotExistsIdDto = new PostCategoryRequestDto();
        updateNotExistsIdDto.setViewName("Q&A수정");
        updateNotExistsIdDto.setUrlName("questions22222");
        updateNotExistsIdDto.setOrders(1);
        updateNotExistsIdDto.setAdminWriteYn("N");
        updateNotExistsIdDto.setUrl("/board/questions222");
        updateNotExistsIdDto.setUseYn("Y");

        //when

        //동일한 urlName으로 수정하는 경우 예외 반환
        assertThatThrownBy(() ->
            iPostCategoryService.updatePostCategory(savedCategoryEntity.getId(), updateEqualUrlNameDto)
        ).isInstanceOf(CommonException.class);

        //동일한 url로 수정하는 경우 예외 반환
        assertThatThrownBy(() ->
                iPostCategoryService.updatePostCategory(savedCategoryEntity.getId(), updateEqualUrlDto)
        ).isInstanceOf(CommonException.class);

        //존재하지 않는 postCategoryId로 수정하는 경우 예외 반환
        assertThatThrownBy(() ->
                iPostCategoryService.updatePostCategory(-1L, updateNotExistsIdDto)
        ).isInstanceOf(CommonException.class);

    }

    @Test
    public void deletePostCategory_게시판_카테고리_삭제_성공() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions");
        postCategoryRequestDto.setUseYn("Y");
        PostCategoryEntity savedCategoryEntity = iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        //when
        iPostCategoryService.deletePostCategory(savedCategoryEntity.getId());

        //then
        assertThat(savedCategoryEntity.getUseYn()).isEqualTo("N");

    }

    @Test
    public void deletePostCategory_게시판_카테고리_삭제_실패() {
        //given
        PostCategoryRequestDto postCategoryRequestDto = new PostCategoryRequestDto();
        postCategoryRequestDto.setViewName("Q&A");
        postCategoryRequestDto.setUrlName("questions");
        postCategoryRequestDto.setOrders(1);
        postCategoryRequestDto.setAdminWriteYn("N");
        postCategoryRequestDto.setUrl("/board/questions");
        postCategoryRequestDto.setUseYn("Y");
        PostCategoryEntity savedCategoryEntity = iPostCategoryRepository.save(PostCategoryRequestDto.mapToEntity(postCategoryRequestDto));

        //when
        assertThatThrownBy(() ->
                iPostCategoryService.deletePostCategory(-1L)
        ).isInstanceOf(CommonException.class);

    }

}
