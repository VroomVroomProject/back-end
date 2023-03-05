package com.backend.vroomvroom.repository.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMenuRepository extends JpaRepository<MenuEntity, String> {
    MenuEntity findByMenuId(String menuId);

    List<MenuEntity> findByGroupIdx(Long groupIdx);

    List<MenuEntity> findByUseYn(String useYn);

    @Query(value = "update tm_menu set sort_no = sort_no + 1 where sort_no > :sortNo and group_idx = :menuNo", nativeQuery = true)
    void updateSort(@Param("menuNo") Long menuNo, @Param("sortNo") Long sortNo);

    @Query(value = "select case when max(group_idx) is not null then max(group_idx) else 0 end from tm.menu", nativeQuery = true)
    Long maxGroupIdx();
}
