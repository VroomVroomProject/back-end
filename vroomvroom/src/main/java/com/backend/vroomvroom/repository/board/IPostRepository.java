package com.backend.vroomvroom.repository.board;

import com.backend.vroomvroom.entity.board.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface IPostRepository extends JpaRepository<PostEntity, Long>, QuerydslPredicateExecutor<PostEntity> {
}
