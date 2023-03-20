package com.backend.vroomvroom.service.board;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.dto.board.request.PostRequestDto;
import com.backend.vroomvroom.dto.board.response.PostPageResponse;
import com.backend.vroomvroom.dto.board.response.PostResponseDto;
import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.board.QPostEntity;
import com.backend.vroomvroom.repository.board.IPostCategoryRepository;
import com.backend.vroomvroom.repository.board.IPostRepository;
import com.backend.vroomvroom.repository.comment.ICommentRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.backend.vroomvroom.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final IPostRepository iPostRepository;
    private final IPostCategoryRepository iPostCategoryRepository;
    private final ICommentRepository iCommentRepository;

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
                    throw new CommonException(NOT_FOUND_ENTITY, "게시판 카테고리를 찾을 수 없습니다. id : " + postRequestDto.getPostCategoryId());
                });

        PostEntity postEntity = PostRequestDto.mapToEntity(postRequestDto, findPostCategory);
        PostEntity savedPost = iPostRepository.save(postEntity);

        return PostResponseDto.mapToDto(savedPost);
    }


    @Override
    @Transactional
    public PostPageResponse getPostAll(Pageable pageable, String urlName, String type, String keyword) {

        if(!iPostCategoryRepository.existsByUrlName(urlName)) {
            log.error("urlName 값과 일치하는 게시판이 존재하지 않습니다. urlName : {}", urlName);
            throw new CommonException(DUPLICATED_ENTITY, "urlName과 일치하는 게시판 카테고리를 찾을 수 없습니다. urlName : " + urlName);
        }
//        BooleanBuilder booleanBuilder = getPostCondition(urlName);
        BooleanBuilder booleanBuilder1 = new BooleanBuilder().and(getPostCondition(urlName)).and(getSearchCondition(type, keyword));

        Page<PostResponseDto> postResponseDtoPage = iPostRepository.findAll(booleanBuilder1, pageable).map(PostResponseDto::mapToDto);
        
        return PostPageResponse.mapToDto(pageable, postResponseDtoPage);
    }

    /**
     * 게시판 조회 조건
     */
    private BooleanBuilder getPostCondition(String urlName) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPostEntity qPostEntity = QPostEntity.postEntity;

        //게시물 조회 공통 조건(id > 0 and useYn == "Y")
        booleanBuilder.and(qPostEntity.id.gt(0L));
        booleanBuilder.and(qPostEntity.useYn.eq("Y"));

        //전체 게시판인 경우 공지 게시물을 제외한 모든 게시물 조회
        if(urlName.equals("all")) {
            booleanBuilder.and(qPostEntity.noticeYn.eq("N"));
            return booleanBuilder;
        }

        //공지사항 게시판일 경우 공지 게시물만 조회, 다른 게시판인 경우에는 공지 게시물 제외
        if(urlName.equals("notice")) {
            booleanBuilder.and(qPostEntity.noticeYn.eq("Y"));
        }else {
            booleanBuilder.and(qPostEntity.noticeYn.eq("N"));
        }

        booleanBuilder.and(qPostEntity.postCategory.urlName.eq(urlName));

        return booleanBuilder;
    }

    /**
     * 게시물 검색 조건
     */
    private BooleanBuilder getSearchCondition(String type, String keyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPostEntity qPostEntity = QPostEntity.postEntity;
        
        //검색어가 없는 경우
        if(!StringUtils.hasText(keyword)) {
            return booleanBuilder;
        }

        if(type.equals("title")) {
            booleanBuilder.and(qPostEntity.title.contains(keyword));
        }

        if(type.equals("contents")) {
            booleanBuilder.and(qPostEntity.contents.contains(keyword));
        }

        return booleanBuilder;
    }

    @Override
    @Transactional
    public PostResponseDto getPostDetail(String urlName, Long postId) {

        if(!iPostCategoryRepository.existsByUrlName(urlName)) {
            log.error("urlName과 일치하는 게시판이 존재하지 않습니다. : {}", urlName);
            throw new CommonException(DUPLICATED_ENTITY, "urlName과 일치하는 게시판 카테고리를 찾을 수 없습니다. urlName : " + urlName);
        }

        PostEntity findPostEntity = iPostRepository.findById(postId).orElseThrow(() -> {
            log.error("postId가 존재하지 않습니다. postId : {}", postId);
            throw new CommonException(NOT_FOUND_ENTITY, "게시물을 찾을 수 없습니다. postId : " + postId);
        });

        if(findPostEntity.getUseYn().equals("N")) {
            log.error("삭제된 게시물은 조회할 수 없습니다. postId : {}", postId);
            throw new CommonException(NOT_FOUND_ENTITY, "삭제된 게시물은 조회할 수 없습니다. postId : " + postId);
        }

        return PostResponseDto.mapToDto(findPostEntity);
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, PostRequestDto postRequestDto) {

        PostCategoryEntity findPostCategory = iPostCategoryRepository.findById(postRequestDto.getPostCategoryId()).orElseThrow(() -> {
            log.error("postCategoryId가 존재하지 않습니다. postCategoryId : {}", postRequestDto.getPostCategoryId());
            throw new CommonException(NOT_FOUND_ENTITY, "게시판 카테고리를 찾을 수 없습니다. id : " + postRequestDto.getPostCategoryId());
        });


        PostEntity findPost = iPostRepository.findById(postId).orElseThrow(() -> {
            log.error("postId가 존재하지 않습니다. postId : {}", postId);
            throw new CommonException(NOT_FOUND_ENTITY, "게시물을 찾을 수 없습니다. postId : " + postId);
        });

        findPost.update(postRequestDto, findPostCategory);

        return findPost.getId();
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        PostEntity findPost = iPostRepository.findById(postId).orElseThrow(() -> {
            log.error("postId가 존재하지 않습니다. postId : {}", postId);
            throw new CommonException(NOT_FOUND_ENTITY, "게시물을 찾을 수 없습니다. postId : " + postId);
        });

        findPost.delete();
        iCommentRepository.bulkUseYnSetting("N", findPost.getId());
    }

}
