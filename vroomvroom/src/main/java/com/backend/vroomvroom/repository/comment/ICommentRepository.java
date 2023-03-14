package com.backend.vroomvroom.repository.comment;

import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostAndUseYnOrderByCreateDateTime(PostEntity post, String useYn);
}
