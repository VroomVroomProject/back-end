package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import com.backend.vroomvroom.repository.menu.IMenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
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
        List<MenuEntity> rs = menuRepository.findByUseYn("Y");
        rs = rs.stream().sorted(Comparator.comparing(MenuEntity::getGroupIdx).reversed().thenComparing(MenuEntity::getSortNo))
                .collect(Collectors.toList());
        List<MenuDto> menuList = new ArrayList<>();
        BeanUtils.copyProperties(rs, menuList);
        return menuList;
    }

    @Override
    public List<MenuDto> listAll() {
        return null;
    }

    @Transactional
    @Override
    public MenuDto updateMenu(MenuDto menuDto) {

        MenuEntity updateEntity = menuRepository.findById(menuDto.getMenuId()).orElse(null);
        List<MenuEntity> groupList = menuRepository.findByGroupIdx(menuDto.getGroupIdx()); //groupIdx 리스트

        if(updateEntity.getMenuId().equals(menuDto.getParentId())) { //자기 자신의 부모가 아닐때
//            if(menuDto.getSortNo() ==)
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteMenu(MenuDto menuDto) {
        boolean rs = false;

        MenuEntity menuEntity = menuRepository.findById(menuDto.getMenuId()).orElse(null);
        if(ObjectUtils.isNotEmpty(menuEntity) && menuEntity.getGroupIdx().equals(menuDto.getGroupIdx())) {
            menuEntity.setUseYn("N");

            List<MenuEntity> menuList = menuRepository.findByGroupIdx(menuDto.getGroupIdx());
            for(MenuEntity entity : menuList) {
                if(entity.getSortNo() > menuEntity.getSortNo()) {
                    entity.setSortNo(entity.getSortNo() - 1);
                }
            }
            rs = true;
        }
        return rs;
    }
}
