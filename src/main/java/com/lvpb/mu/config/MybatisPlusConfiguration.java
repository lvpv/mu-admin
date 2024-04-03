package com.lvpb.mu.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lvpb.mu.handler.FieldMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 17:40
 * @description MybatisPlusConfiguration Description
 */
@Configuration
@MapperScan("com.lvpb.mu.modules.*.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisPlusConfiguration {


    /**
     * 数据自动填充插件
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new FieldMetaObjectHandler();
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 禁止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }
}
