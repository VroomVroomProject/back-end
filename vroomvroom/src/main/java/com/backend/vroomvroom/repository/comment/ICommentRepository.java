package com.backend.vroomvroom.repository.comment;

import com.backend.vroomvroom.entity.board.PostEntity;
import com.backend.vroomvroom.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostAndUseYnOrderByCreateDateTime(PostEntity post, String useYn);

    @Modifying(clearAutomatically = true)
    @Query("update CommentEntity c set c.useYn = :useYn where c.post.id = :postId")
    int bulkUseYnSetting(@Param("useYn") String useYn, @Param("postId") Long postId);
}
