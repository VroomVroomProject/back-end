package com.backend.vroomvroom.repository.board;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostCategoryRepository extends JpaRepository<PostCategoryEntity, Long> {
}
