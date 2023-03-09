package com.backend.vroomvroom.repository.board;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostCategoryRepository extends JpaRepository<PostCategoryEntity, Long> {

    List<PostCategoryEntity> findCategoryListByUseYnOrderByOrders(String useYn);

    boolean existsById(Long id);

    boolean existsByUrlNameOrUrl(String urlName, String url);
}
