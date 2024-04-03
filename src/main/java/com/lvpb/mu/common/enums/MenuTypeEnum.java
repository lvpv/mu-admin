package com.lvpb.mu.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:18
 * @description MenuTypeEnum
 */

@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    DIRECTORY(1, "目录"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮");

    @EnumValue
    private final int type;

    private final String name;

    public static MenuTypeEnum of(int type) {
        for (MenuTypeEnum value : MenuTypeEnum.values()) {
            if (value.getType() == type) {
                return value;
            }
        }
        return null;
    }


}
