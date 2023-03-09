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
        
        //urlName 또는 url에 대한 같은 값이 이미 존재하는 경우
        if(iPostCategoryRepository.existsByUrlNameOrUrl(postCategoryRequestDto.getUrlName(), postCategoryRequestDto.getUrl())) {
            log.error("urlName 또는 url에 대한 값이 이미 존재합니다.");
            throw new RuntimeException("urlName 또는 url에 대한 값이 이미 존재합니다.");
        }

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
}
