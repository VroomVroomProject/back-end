package com.backend.vroomvroom.repository.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuRepository extends JpaRepository<MenuEntity, String> {
    MenuDto findByMenuId(String menuId);
}
