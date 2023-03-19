package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.request.CreateMenuDto;
import com.backend.vroomvroom.dto.menu.request.UpdateMenuDto;
import com.backend.vroomvroom.dto.menu.response.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import com.backend.vroomvroom.repository.menu.IMenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService{
    private final IMenuRepository menuRepository;

    @Transactional
    @Override
    public MenuDto createMenu(CreateMenuDto createMenuDto) throws Exception {
        MenuEntity menuEntity = menuRepository.findById(createMenuDto.getMenuId()).orElse(null);

        if(menuEntity != null) return null;

        if (ObjectUtils.isEmpty(menuEntity)) {
            Long level = 1L;
            Long sort = 0L;
            Long maxSort = menuRepository.maxGroupIdx();
            if (StringUtils.isBlank(createMenuDto.getParentId())) {
                menuEntity = MenuEntity.builder()
                        .menuId(createMenuDto.getMenuId())
                        .menuName(createMenuDto.getMenuName())
                        .menuPath(createMenuDto.getMenuPath())
                        .parentId(createMenuDto.getMenuId())
                        .groupIdx(++maxSort)
                        .levelNo(sort)
                        .sortNo(level)
                        .useYn("Y")
                        .build();
            }else{
                MenuEntity parentMenu = menuRepository.findByMenuId(createMenuDto.getParentId()); //선택값의 부모
//                MenuEntity rootMenu = menuRepository.findByGroupIdx(createMenuDto.getGroupIdx());
//                List<MenuEntity> rootMenuList = menuRepository.findByGroupIdx(createMenuDto.getGroupIdx());
//                MenuEntity rootMenu = rootMenuList.stream().filter(x -> x.getSortNo() == 0).collect(Collectors.toList()).get(0);
//                MenuEntity rootMenu = menuRepository.findByGroupIdx(menuDto.getGroupIdx()); //선택값의 루트부모

                Long selectLevel = parentMenu.getLevelNo();
                Long selectSort = parentMenu.getSortNo();

                menuRepository.updateSort(parentMenu.getGroupIdx(), parentMenu.getSortNo());
                menuEntity = MenuEntity.builder()
                        .menuId(createMenuDto.getMenuId())
                        .menuName(createMenuDto.getMenuName())
                        .menuPath(createMenuDto.getMenuPath())
                        .parentId(createMenuDto.getParentId())
                        .useYn("Y")
                        .groupIdx(parentMenu.getGroupIdx()) //여기서 에러남
                        .levelNo(++selectLevel)
                        .sortNo(++selectSort)
                        .build();
            }
            menuRepository.save(menuEntity);
        } else {
            throw new Exception("중복된 아이디입니다");
        }
        return new MenuDto(menuEntity);
    }

    @Override
    public MenuDto getDetailMenu(String menuId) {
        MenuEntity detailMenu = menuRepository.findById(menuId).orElse(null);
        return new MenuDto(detailMenu);
    }

    @Override
    public List<MenuDto> list() {
        List<MenuEntity> rs = menuRepository.findByUseYn("Y");
        return rs.stream().sorted(Comparator.comparing(MenuEntity::getGroupIdx).reversed().thenComparing(MenuEntity::getSortNo)).map(MenuDto::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuDto> listAll() {
        List<MenuEntity> rs = menuRepository.findAll();
        return rs.stream().map(MenuDto::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MenuDto updateMenu(UpdateMenuDto updateMenuDto) {

        MenuEntity updateEntity = menuRepository.findById(updateMenuDto.getMenuId()).orElse(null);
        List<MenuEntity> groupList = menuRepository.findByGroupIdxList(updateMenuDto.getGroupIdx()); //groupIdx 리스트
        List<String> childList = menuRepository.findParentListByMenuId(updateMenuDto.getMenuId());
        int menuChildSize = childList.size() == 0 ? 0 : childList.size(); //자식이 있는 리스트일 경우 자식 + 1 본인 포함 else 0 + 1

        if(!updateEntity.getMenuId().equals(updateMenuDto.getParentId())) { //자기 자신의 부모가 아닐때
            if(!(Objects.equals(updateMenuDto.getSortNo(), updateMenuDto.getChangeSortNo()))) {
                for(MenuEntity item : groupList) {
                    if(item.getUseYn().equals("Y") && (item.getSortNo() >= updateMenuDto.getSortNo()) && item.getSortNo() != 0) {
                        if(menuChildSize == 0) {
                            item.setSortNo(item.getSortNo() + 1);
                        }else if(menuChildSize > 0) {
                            item.setSortNo(item.getSortNo() + 1);
                        }
                    }
                }
                for (MenuEntity item : groupList) {
                    if (item.getUseYn().equals("Y") && (item.getSortNo() < updateMenuDto.getSortNo()) && item.getSortNo() != 0) {
                        if (menuChildSize == 0) {
                            item.setSortNo(item.getSortNo() - 1);
                        } else if (menuChildSize > 0) {
                            item.setSortNo(item.getSortNo() - 1);
                        }
                    }
                }

            }
        }

//        updateEntity = MenuEntity.builder()
//                .menuName(updateMenuDto.getMenuName())
//                .menuPath(updateMenuDto.getMenuPath())
//                .parentId(updateMenuDto.getParentId())
//                .levelNo(updateMenuDto.getLevelNo())
//                .sortNo(updateMenuDto.getSortNo())
//                .groupIdx(updateMenuDto.getGroupIdx())
//                        .build();

        updateEntity.updateMenu(updateMenuDto.getMenuName(), updateMenuDto.getMenuPath(), updateMenuDto.getParentId(), updateMenuDto.getGroupIdx());
        return MenuDto.mapToDto(updateEntity);
    }

    @Transactional
    @Override
    public boolean deleteMenu(String menuId) {
        boolean rs = false;

        MenuEntity menuEntity = menuRepository.findById(menuId).orElse(null);
        // && menuEntity.getGroupIdx().equals(menuDto.getGroupIdx())
        if(ObjectUtils.isNotEmpty(menuEntity)) {
            menuEntity.delete();

            List<MenuEntity> menuList = menuRepository.findByGroupIdxList(menuEntity.getGroupIdx());
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
