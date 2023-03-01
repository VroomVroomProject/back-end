package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import com.backend.vroomvroom.repository.menu.IMenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService{
    private final IMenuRepository menuRepository;
    @Override
    public MenuDto createMenu(MenuDto menuDto) throws Exception {

        MenuEntity menuEntity = menuRepository.findById(menuDto.getMenuId()).orElse(null);

        if (ObjectUtils.isEmpty(menuEntity)) {
            MenuEntity createMenu = null;
            if (StringUtils.isBlank(menuDto.getParentId())) {
                createMenu = MenuEntity.builder()
                        .menuId(menuDto.getMenuId())
                        .menuName(menuDto.getMenuName())
                        .menuPath(menuDto.getMenuPath())
                        .parentId(menuDto.getMenuId())
                        .groupIdx(menuDto.getGroupIdx())
                        .levelNo(0L)
                        .sortNo(1L)
                        .build();
            }else{
                createMenu = MenuEntity.builder()
                        .menuId(menuDto.getMenuId())
                        .menuName(menuDto.getMenuName())
                        .menuPath(menuDto.getMenuPath())
                        .parentId(menuDto.getParentId())
                        .groupIdx(menuDto.getGroupIdx())
                        .levelNo(menuDto.getLevelNo())
                        .sortNo(menuDto.getSortNo())
                        .build();
            }
            menuRepository.save(createMenu);
        } else {
            throw new Exception("중복된 아이디입니다");
        }

        return menuDto;
    }

    @Override
    public MenuDto getDetailMenu(String menuId) {
        MenuDto detailMenu = menuRepository.findByMenuId(menuId);
        return detailMenu;
    }

    @Override
    public List<MenuDto> list() {
        return null;
    }

    @Override
    public List<MenuDto> listAll() {
        return null;
    }

    @Override
    public MenuDto updateMenu() {
        return null;
    }

    @Override
    public boolean deleteMenu() {
        return false;
    }
}
