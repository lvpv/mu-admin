package com.lvpb.mu.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvpb.mu.common.domain.PageRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/8 16:39
 * @description PageUtils Description
 */
public class PageUtils {

    public static PageRequest validatePageRequest(PageRequest pageRequest){
        Long page = pageRequest.getPage();
        Long size = pageRequest.getSize();
        if (Objects.isNull(page) || page < 0){
            page = 1L;
        }
        pageRequest.setPage(page);
        if (Objects.isNull(size) || size < 0){
            size = 10L;
        }
        pageRequest.setSize(size);
        return pageRequest;
    }

    public static <T> Page<T> buildPage(PageRequest pageParam) {
        // 页码 + 数量
        Page<T> page = new Page<>(pageParam.getPage(), pageParam.getSize());
        List<PageRequest.SortItem> sortItems = pageParam.getSortItems();
        // 排序字段
        if (!CollectionUtil.isEmpty(sortItems)) {
            page.addOrder(sortItems.stream().map(sortItem -> sortItem.isAsc() ?
                            OrderItem.asc(sortItem.getColumn()) : OrderItem.desc(sortItem.getColumn()))
                    .collect(Collectors.toList()));
        }else {
            page.addOrder(OrderItem.desc("create_time"));
        }
        return page;
    }
}
