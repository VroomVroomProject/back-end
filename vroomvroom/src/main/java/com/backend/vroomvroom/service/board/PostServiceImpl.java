package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import com.backend.vroomvroom.repository.board.IPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final IPostRepository iPostRepository;
    private final IPostCategoryRepository iPostCategoryRepository;

    @Override
    @Transactional
    public PostResponseDto postRegister(PostRequestDto postRequestDto) {

        // TODO: 2023-03-08 아래 주석 부분 user 서비스 구현 후에 변경 그리고 테스트 코드 작성할 것 (entity에도 주석 해놓았음)
//        User findUser = iUserRepository.findById(postRequestDto.getUserId())
//                .orElseThrow(() -> {
//                    log.error("user가 존재하지 않습니다. userId : {}", postRequestDto.getUserId());
//                    throw new RuntimeException("can`t find a user by " + " userId " + postRequestDto.getUserId());
//                });

        PostCategoryEntity findPostCategory = iPostCategoryRepository.findById(postRequestDto.getPostCategoryId())
                .orElseThrow(() -> {
                    log.error("postCategoryId가 존재하지 않습니다. postCategoryId : {}", postRequestDto.getPostCategoryId());
                    throw new RuntimeException("can`t find a postCategoryId by " + " postCategoryId " + postRequestDto.getPostCategoryId());
                });

        PostEntity postEntity = PostRequestDto.mapToEntity(postRequestDto, findPostCategory);
        PostEntity savedPost = iPostRepository.save(postEntity);

        return PostResponseDto.mapToDto(savedPost);
    }
}
