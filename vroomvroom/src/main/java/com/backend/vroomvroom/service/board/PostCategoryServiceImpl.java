package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.PostCategoryCreateDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements IPostCategoryService {

    private final IPostCategoryRepository iPostCategoryRepository;

    // TODO: 2023-03-07 createUserId -> 회원 기능 구현시에 로그인한 회원 ID로 변경할 것
    @Override
    @Transactional
    public PostCategoryCreateDto createPostCategory(PostCategoryCreateDto postCategoryCreateDto) {
        PostCategoryEntity savePostCategory = iPostCategoryRepository.save(PostCategoryEntity.builder()
                .name(postCategoryCreateDto.getName())
                .orders(postCategoryCreateDto.getOrders())
                .adminWriteYn(postCategoryCreateDto.getAdminWriteYn())
                .url(postCategoryCreateDto.getUrl())
                .useYn("Y")
                .createUserId("1").build());

        log.info("createPostCategory result  : {}", savePostCategory);

        postCategoryCreateDto.setCreateUserId(savePostCategory.getCreateUserId());
        postCategoryCreateDto.setCreateDateTime(savePostCategory.getCreateDateTime());
        postCategoryCreateDto.setUseYn(savePostCategory.getUseYn());


        return postCategoryCreateDto;
    }
}
