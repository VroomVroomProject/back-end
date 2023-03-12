package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.dto.board.response.PostCategoryResponseDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements IPostCategoryService {

    private final IPostCategoryRepository iPostCategoryRepository;

    @Override
    @Transactional
    public PostCategoryResponseDto createPostCategory(PostCategoryRequestDto postCategoryRequestDto) {

        // TODO: 2023-03-12 생성을 요청하는 userId가 user테이블에 존재하지 않을 경우의 예외처리 하기

        this.validatePostCategory(postCategoryRequestDto.getUrlName(), postCategoryRequestDto.getUrl());

        PostCategoryEntity postCategoryEntity = PostCategoryRequestDto.mapToEntity(postCategoryRequestDto);
        PostCategoryEntity savePostCategory = iPostCategoryRepository.save(postCategoryEntity);

        log.info("createPostCategory result  : {}", savePostCategory);

        return PostCategoryResponseDto.mapToDto(savePostCategory);
    }

    @Override
    @Transactional
    public List<PostCategoryResponseDto> listPostCategory(String useYn) {
        List<PostCategoryEntity> rs = iPostCategoryRepository.findCategoryListByUseYnOrderByOrders(useYn);

        return rs.stream().map(PostCategoryResponseDto :: mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long updatePostCategory(Long postCategoryId, PostCategoryRequestDto postCategoryRequestDto) {
        // TODO: 2023-03-12 수정을 요청하는 userId가 user테이블에 존재하지 않을 경우의 예외처리 하기

        this.validatePostCategory(postCategoryRequestDto.getUrlName(), postCategoryRequestDto.getUrl());

        PostCategoryEntity findCategoryEntity = iPostCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> {
                    log.error("postCategoryId가 존재하지 않습니다. postCategoryId : {}", postCategoryId);
                    throw new RuntimeException("can`t find a postCategoryId by " + " postCategoryId " + postCategoryId);
                });

        findCategoryEntity.update(postCategoryRequestDto);

        return findCategoryEntity.getId();
    }

    @Override
    @Transactional
    public void deletePostCategory(Long postCategoryId) {
        // TODO: 2023-03-12 삭제를 요청하는 userId가 user테이블에 존재하지 않을 경우의 예외처리 하기
        PostCategoryEntity findCategoryEntity = iPostCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> {
                    log.error("postCategoryId가 존재하지 않습니다. postCategoryId : {}", postCategoryId);
                    throw new RuntimeException("can`t find a postCategoryId by " + " postCategoryId " + postCategoryId);
                });

        findCategoryEntity.delete();

    }

    private void validatePostCategory(String urlName, String url) {

        if(iPostCategoryRepository.existsByUrlName(urlName)) {
            log.error("urlName 값이 이미 존재합니다. urlName : {}", urlName);
            throw new RuntimeException("urlName 값이 이미 존재합니다. " + " urlName : " + urlName);
        }

        if(iPostCategoryRepository.existsByUrl(url)) {
            log.error("url 값이 이미 존재합니다. url : {}", url);
            throw new RuntimeException("url 값이 이미 존재합니다. " + " url : " + url);
        }
    }
}
