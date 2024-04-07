package com.lvpb.mu.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lvpb.mu.common.entity.BaseEntity;
import com.lvpb.mu.security.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 18:59
 * @description FieldMetaObjectHandler Description
 */


public class FieldMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
            DateTime date = DateUtil.date();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseEntity.getCreateTime())) {
                baseEntity.setCreateTime(date);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseEntity.getUpdateTime())) {
                baseEntity.setUpdateTime(date);
            }

            Long userId = SecurityUtils.getLoginUserId();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getCreator())) {
                baseEntity.setCreator(userId);
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getUpdater())) {
                baseEntity.setUpdater(userId);
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
            DateTime date = DateUtil.date();
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseEntity.getUpdateTime())) {
                baseEntity.setUpdateTime(date);
            }
            Long userId = SecurityUtils.getLoginUserId();

            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getUpdater())) {
                baseEntity.setUpdater(userId);
            }
        }
    }
}
