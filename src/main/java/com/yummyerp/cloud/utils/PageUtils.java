package com.yummyerp.cloud.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.common.dto.QueryCondition;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * 分页工具类
 *
 * @author 周欢
 * @since 2025-07-28
 */
public class PageUtils {

    /**
     * 执行分页查询（使用QueryWrapperBuilder）
     *
     * @param service      服务实例
     * @param pageRequest  分页请求参数
     * @param queryBuilder 查询条件构建器
     * @param <T>          实体类型
     * @return 分页结果
     */
    public static <T> PageResult<T> page(IService<T> service, 
                                        PageRequest pageRequest, 
                                        Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> queryBuilder) {
        // 创建分页对象
        Page<T> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        
        // 构建查询条件
        QueryWrapperBuilder<T> builder = QueryWrapperBuilder.create();
        if (queryBuilder != null) {
            builder = queryBuilder.apply(builder);
        }
        
        // 执行分页查询
        Page<T> result = service.page(page, builder.build());
        
        // 转换为统一的分页结果
        return PageResult.of(result);
    }
    
    /**
     * 执行分页查询（兼容旧版本）
     *
     * @param service      服务实例
     * @param pageRequest  分页请求参数
     * @param queryBuilder 查询条件构建器
     * @param <T>          实体类型
     * @return 分页结果
     */
    @Deprecated
    public static <T> PageResult<T> pageLegacy(IService<T> service, 
                                              PageRequest pageRequest, 
                                              Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> queryBuilder) {
        // 创建分页对象
        Page<T> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        
        // 构建查询条件
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        if (queryBuilder != null) {
            queryWrapper = queryBuilder.apply(queryWrapper);
        }
        
        // 执行分页查询
        Page<T> result = service.page(page, queryWrapper);
        
        // 转换为统一的分页结果
        return PageResult.of(result);
    }

    /**
     * 执行分页查询（无查询条件）
     *
     * @param service     服务实例
     * @param pageRequest 分页请求参数
     * @param <T>         实体类型
     * @return 分页结果
     */
    public static <T> PageResult<T> page(IService<T> service, PageRequest pageRequest) {
        return page(service, pageRequest, null);
    }
    
    /**
     * 执行分页查询（基于QueryCondition）
     *
     * @param service       服务实例
     * @param pageRequest   分页请求参数
     * @param condition     查询条件
     * @param customBuilder 自定义条件构建器
     * @param <T>           实体类型
     * @return 分页结果
     */
    public static <T> PageResult<T> page(IService<T> service, 
                                        PageRequest pageRequest,
                                        QueryCondition condition,
                                        Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> customBuilder) {
        return page(service, pageRequest, builder -> {
            // 先应用基础条件
            if (condition != null) {
                condition.hasStatus();// 这里需要子类重写来指定具体字段
                condition.hasValidTimeRange();// 这里需要子类重写来指定具体字段
            }
            
            // 再应用自定义条件
            if (customBuilder != null) {
                builder = customBuilder.apply(builder);
            }
            
            return builder;
        });
    }

    // ==================== 新版本的条件构建器工具方法 ====================
    
    /**
     * 创建查询构建器
     */
    public static <T> Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> like(
            SFunction<T, String> fieldGetter, String value) {
        return builder -> builder.like(fieldGetter, value);
    }
    
    public static <T, V> Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> eq(
            SFunction<T, V> fieldGetter, V value) {
        return builder -> builder.eq(fieldGetter, value);
    }
    
    public static <T, V> Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> between(
            SFunction<T, V> fieldGetter, V start, V end) {
        return builder -> builder.between(fieldGetter, start, end);
    }
    
    public static <T, V> Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> orderByDesc(
            SFunction<T, V> fieldGetter) {
        return builder -> builder.orderByDesc(fieldGetter);
    }

    @SafeVarargs
    public static <T> Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> combine(
            Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>>... builders) {
        return builder -> {
            for (Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> builderFunc : builders) {
                if (builderFunc != null) {
                    builder = builderFunc.apply(builder);
                }
            }
            return builder;
        };
    }
    
    // ==================== 旧版本兼容方法 ====================
    
    /**
     * 构建模糊查询条件（字符串字段）
     */
    @Deprecated
    public static <T> Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> likeLegacy(
            SFunction<T, String> fieldGetter, String value) {
        return wrapper -> {
            if (StringUtils.isNotBlank(value)) {
                wrapper.like(fieldGetter, value);
            }
            return wrapper;
        };
    }

    /**
     * 构建等值查询条件
     */
    @Deprecated
    public static <T, V> Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> eqLegacy(
            SFunction<T, V> fieldGetter, V value) {
        return wrapper -> {
            if (value != null) {
                wrapper.eq(fieldGetter, value);
            }
            return wrapper;
        };
    }

    /**
     * 构建范围查询条件
     */
    @Deprecated
    public static <T, V> Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> betweenLegacy(
            SFunction<T, V> fieldGetter, V start, V end) {
        return wrapper -> {
            if (start != null && end != null) {
                wrapper.between(fieldGetter, start, end);
            }
            return wrapper;
        };
    }

    /**
     * 构建排序条件（降序）
     */
    @Deprecated
    public static <T, V> Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> orderByDescLegacy(
            SFunction<T, V> fieldGetter) {
        return wrapper -> wrapper.orderByDesc(fieldGetter);
    }

    /**
     * 构建排序条件（升序）
     */
    @Deprecated
    public static <T, V> Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> orderByAscLegacy(
            SFunction<T, V> fieldGetter) {
        return wrapper -> wrapper.orderByAsc(fieldGetter);
    }

    /**
     * 组合多个查询条件
     */
    @SafeVarargs
    @Deprecated
    public static <T> Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> combineLegacy(
            Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>>... builders) {
        return wrapper -> {
            for (Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> builder : builders) {
                if (builder != null) {
                    wrapper = builder.apply(wrapper);
                }
            }
            return wrapper;
        };
    }
}