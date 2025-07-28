package com.yummyerp.cloud.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yummyerp.cloud.modules.common.dto.QueryCondition;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

/**
 * 通用查询条件构造器
 *
 * @author 周欢
 * @since 2025-07-28
 */
public class QueryWrapperBuilder<T> {

    private final LambdaQueryWrapper<T> wrapper;

    public QueryWrapperBuilder() {
        this.wrapper = new LambdaQueryWrapper<>();
    }

    public QueryWrapperBuilder(LambdaQueryWrapper<T> wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * 创建新的构造器
     */
    public static <T> QueryWrapperBuilder<T> create() {
        return new QueryWrapperBuilder<>();
    }

    /**
     * 基于现有wrapper创建构造器
     */
    public static <T> QueryWrapperBuilder<T> create(LambdaQueryWrapper<T> wrapper) {
        return new QueryWrapperBuilder<>(wrapper);
    }

    // ==================== 基础条件方法 ====================

    /**
     * 等于条件
     */
    public QueryWrapperBuilder<T> eq(boolean condition, SFunction<T, ?> column, Object value) {
        wrapper.eq(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> eq(SFunction<T, ?> column, Object value) {
        return eq(value != null, column, value);
    }

    /**
     * 不等于条件
     */
    public QueryWrapperBuilder<T> ne(boolean condition, SFunction<T, ?> column, Object value) {
        wrapper.ne(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> ne(SFunction<T, ?> column, Object value) {
        return ne(value != null, column, value);
    }

    /**
     * 模糊查询（左右模糊）
     */
    public QueryWrapperBuilder<T> like(boolean condition, SFunction<T, ?> column, String value) {
        wrapper.like(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> like(SFunction<T, ?> column, String value) {
        return like(StringUtils.isNotBlank(value), column, value);
    }

    /**
     * 左模糊查询
     */
    public QueryWrapperBuilder<T> likeLeft(boolean condition, SFunction<T, ?> column, String value) {
        wrapper.likeLeft(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> likeLeft(SFunction<T, ?> column, String value) {
        return likeLeft(StringUtils.isNotBlank(value), column, value);
    }

    /**
     * 右模糊查询
     */
    public QueryWrapperBuilder<T> likeRight(boolean condition, SFunction<T, ?> column, String value) {
        wrapper.likeRight(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> likeRight(SFunction<T, ?> column, String value) {
        return likeRight(StringUtils.isNotBlank(value), column, value);
    }

    /**
     * 大于条件
     */
    public QueryWrapperBuilder<T> gt(boolean condition, SFunction<T, ?> column, Object value) {
        wrapper.gt(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> gt(SFunction<T, ?> column, Object value) {
        return gt(value != null, column, value);
    }

    /**
     * 大于等于条件
     */
    public QueryWrapperBuilder<T> ge(boolean condition, SFunction<T, ?> column, Object value) {
        wrapper.ge(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> ge(SFunction<T, ?> column, Object value) {
        return ge(value != null, column, value);
    }

    /**
     * 小于条件
     */
    public QueryWrapperBuilder<T> lt(boolean condition, SFunction<T, ?> column, Object value) {
        wrapper.lt(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> lt(SFunction<T, ?> column, Object value) {
        return lt(value != null, column, value);
    }

    /**
     * 小于等于条件
     */
    public QueryWrapperBuilder<T> le(boolean condition, SFunction<T, ?> column, Object value) {
        wrapper.le(condition, column, value);
        return this;
    }

    public QueryWrapperBuilder<T> le(SFunction<T, ?> column, Object value) {
        return le(value != null, column, value);
    }

    /**
     * 范围查询
     */
    public QueryWrapperBuilder<T> between(boolean condition, SFunction<T, ?> column, Object start, Object end) {
        wrapper.between(condition, column, start, end);
        return this;
    }

    public QueryWrapperBuilder<T> between(SFunction<T, ?> column, Object start, Object end) {
        return between(start != null && end != null, column, start, end);
    }

    /**
     * IN 查询
     */
    public QueryWrapperBuilder<T> in(boolean condition, SFunction<T, ?> column, Collection<?> values) {
        wrapper.in(condition, column, values);
        return this;
    }

    public QueryWrapperBuilder<T> in(SFunction<T, ?> column, Collection<?> values) {
        return in(values != null && !values.isEmpty(), column, values);
    }

    /**
     * NOT IN 查询
     */
    public QueryWrapperBuilder<T> notIn(boolean condition, SFunction<T, ?> column, Collection<?> values) {
        wrapper.notIn(condition, column, values);
        return this;
    }

    public QueryWrapperBuilder<T> notIn(SFunction<T, ?> column, Collection<?> values) {
        return notIn(values != null && !values.isEmpty(), column, values);
    }

    /**
     * IS NULL 查询
     */
    public QueryWrapperBuilder<T> isNull(SFunction<T, ?> column) {
        wrapper.isNull(column);
        return this;
    }

    /**
     * IS NOT NULL 查询
     */
    public QueryWrapperBuilder<T> isNotNull(SFunction<T, ?> column) {
        wrapper.isNotNull(column);
        return this;
    }

    // ==================== 排序方法 ====================

    /**
     * 升序排序
     */
    public QueryWrapperBuilder<T> orderByAsc(SFunction<T, ?> column) {
        wrapper.orderByAsc(column);
        return this;
    }

    /**
     * 降序排序
     */
    public QueryWrapperBuilder<T> orderByDesc(SFunction<T, ?> column) {
        wrapper.orderByDesc(column);
        return this;
    }

    /**
     * 动态排序
     */
    public QueryWrapperBuilder<T> orderBy(boolean condition, boolean isAsc, SFunction<T, ?> column) {
        wrapper.orderBy(condition, isAsc, column);
        return this;
    }

    // ==================== 高级查询方法 ====================

    /**
     * 多字段模糊查询（OR连接）
     */
    @SafeVarargs
    public final QueryWrapperBuilder<T> likeAny(String value, SFunction<T, String>... columns) {
        if (StringUtils.isNotBlank(value) && columns.length > 0) {
            wrapper.and(w -> {
                for (int i = 0; i < columns.length; i++) {
                    if (i == 0) {
                        w.like(columns[i], value);
                    } else {
                        w.or().like(columns[i], value);
                    }
                }
            });
        }
        return this;
    }

    /**
     * 时间范围查询
     */
    public QueryWrapperBuilder<T> timeRange(SFunction<T, Date> column, Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            wrapper.between(column, startTime, endTime);
        } else if (startTime != null) {
            wrapper.ge(column, startTime);
        } else if (endTime != null) {
            wrapper.le(column, endTime);
        }
        return this;
    }

    /**
     * 基于QueryCondition构建基础查询条件
     */
    public QueryWrapperBuilder<T> buildFromCondition(QueryCondition condition,
                                                    Function<QueryCondition, QueryWrapperBuilder<T>> customBuilder) {
        if (condition == null) {
            return this;
        }

        // 基础条件（这里需要子类指定具体字段映射）
        // eq(condition.getStatus())
        // .timeRange(null, condition.getStartTime(), condition.getEndTime())
        // .eq(condition.getCreateUserId())
        // .eq(condition.getDeptId())
        // .in(null, condition.getIds())
        // .notIn(null, condition.getExcludeIds());

        // 自定义条件
        if (customBuilder != null) {
            customBuilder.apply(condition);
        }

        return this;
    }

    // ==================== 逻辑连接 ====================

    /**
     * AND 条件组
     */
    public QueryWrapperBuilder<T> and(Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> function) {
        wrapper.and(w -> function.apply(new QueryWrapperBuilder<>(w)).build());
        return this;
    }

    /**
     * OR 条件组
     */
    public QueryWrapperBuilder<T> or(Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> function) {
        wrapper.or(w -> function.apply(new QueryWrapperBuilder<>(w)).build());
        return this;
    }

    /**
     * 直接OR
     */
    public QueryWrapperBuilder<T> or() {
        wrapper.or();
        return this;
    }

    // ==================== 构建方法 ====================

    /**
     * 构建最终的LambdaQueryWrapper
     */
    public LambdaQueryWrapper<T> build() {
        return wrapper;
    }

    /**
     * 应用自定义条件构建器
     */
    public QueryWrapperBuilder<T> apply(Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> function) {
        return function.apply(this);
    }

    /**
     * 条件式应用构建器
     */
    public QueryWrapperBuilder<T> applyIf(boolean condition, 
                                         Function<QueryWrapperBuilder<T>, QueryWrapperBuilder<T>> function) {
        return condition ? function.apply(this) : this;
    }
}