package com.backend.vroomvroom.repository.menu;

import com.backend.vroomvroom.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuRepository extends JpaRepository<MenuEntity, String> {
    MenuEntity findByMenuId(String menuId);

    List<MenuEntity> findByGroupIdx(Long groupIdx);

    List<MenuEntity> findByUseYn(String useYn);

    @Modifying(clearAutomatically = true)
    @Query(value = "update tm_menu set sort_no = sort_no + 1 where sort_no > :sortNo and group_idx = :menuNo", nativeQuery = true)
    void updateSort(@Param("menuNo") Long menuNo, @Param("sortNo") Long sortNo);

    @Query(value = "select case when max(group_idx) is not null then max(group_idx) else 0 end from tm_menu", nativeQuery = true)
    Long maxGroupIdx();

    @Query(value = "select menu_id from tm_menu where parent_id = :menuId and use_yn = 'Y'", nativeQuery = true)
    List<MenuEntity> findParentListByMenuId(@Param("menuId") String menuId);
}
