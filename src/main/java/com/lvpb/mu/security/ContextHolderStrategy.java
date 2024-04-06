package com.lvpb.mu.security;

import cn.hutool.core.lang.Assert;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 00:10
 * @description ContextHolderStrategy
 */
public class ContextHolderStrategy implements SecurityContextHolderStrategy {
    /**
     * 使用 TransmittableThreadLocal 作为上下文
     */
    private static final ThreadLocal<SecurityContext> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    @Override
    public void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    @Override
    public SecurityContext getContext() {
        SecurityContext context = CONTEXT_HOLDER.get();
        if (Objects.isNull(context)) {
            context = createEmptyContext();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    @Override
    public void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        CONTEXT_HOLDER.set(context);
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
