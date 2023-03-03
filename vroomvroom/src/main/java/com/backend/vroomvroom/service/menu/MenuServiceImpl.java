package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import com.backend.vroomvroom.repository.menu.IMenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService{
    private final IMenuRepository menuRepository;

    @Override
    public MenuDto createMenu(MenuDto menuDto) throws Exception {
        MenuEntity menuEntity = menuRepository.findById(menuDto.getMenuId()).orElse(null);
        if (ObjectUtils.isEmpty(menuEntity)) {
            Long level = 1L;
            Long sort = 0L;
            Long maxSort = menuRepository.maxGroupIdx();
            if (StringUtils.isBlank(menuDto.getParentId())) {
                menuEntity = MenuEntity.builder()
                        .menuId(menuDto.getMenuId())
                        .menuName(menuDto.getMenuName())
                        .menuPath(menuDto.getMenuPath())
                        .parentId(menuDto.getMenuId())
                        .groupIdx(++maxSort)
                        .levelNo(sort)
                        .sortNo(level)
                        .useYn("Y")
                        .build();
            }else{
                MenuEntity parentMenu = menuRepository.findByMenuId(menuDto.getParentId()); //선택값의 부모
                List<MenuEntity> rootMenuList = menuRepository.findByGroupIdx(menuDto.getGroupIdx());
                MenuEntity rootMenu = rootMenuList.stream().filter(x -> x.getSortNo() == 0).collect(Collectors.toList()).get(0);
//                MenuEntity rootMenu = menuRepository.findByGroupIdx(menuDto.getGroupIdx()); //선택값의 루트부모

                Long selectLevel = parentMenu.getLevelNo();
                Long selectSort = parentMenu.getSortNo();

                menuRepository.updateSort(parentMenu.getGroupIdx(), parentMenu.getSortNo());
                menuEntity = MenuEntity.builder()
                        .menuId(menuDto.getMenuId())
                        .menuName(menuDto.getMenuName())
                        .menuPath(menuDto.getMenuPath())
                        .parentId(menuDto.getParentId())
                        .useYn("Y")
                        .groupIdx(rootMenu.getGroupIdx())
                        .levelNo(++selectLevel)
                        .sortNo(++selectSort)
                        .build();
            }
            menuRepository.save(menuEntity);
        } else {
            throw new Exception("중복된 아이디입니다");
        }
        BeanUtils.copyProperties(menuEntity, menuDto);
        return menuDto;
    }

    @Override
    public MenuDto getDetailMenu(String menuId) {
        MenuEntity detailMenu = menuRepository.findById(menuId).orElse(null);
        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(detailMenu, menuDto);
        return menuDto;
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
    public MenuDto updateMenu(MenuDto menuDto) {

        MenuEntity updateEntity = menuRepository.findById(menuDto.getMenuId()).orElse(null);
        List<MenuEntity> groupList = menuRepository.findByGroupIdx(menuDto.getGroupIdx()); //groupIdx 리스트

        return null;
    }

    @Override
    public boolean deleteMenu() {
        return false;
    }
}
