package com.yummyerp.cloud.modules.common.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页返回对象
 *
 * @param <T> 数据类型
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@NoArgsConstructor
@ApiModel(description = "分页返回结果")
public class PageResult<T> {

    @ApiModelProperty(value = "当前页码")
    private Long current;

    @ApiModelProperty(value = "每页大小")
    private Long size;

    @ApiModelProperty(value = "总记录数")
    private Long total;

    @ApiModelProperty(value = "总页数")
    private Long pages;

    @ApiModelProperty(value = "数据列表")
    private List<T> records;

    @ApiModelProperty(value = "是否有上一页")
    private Boolean hasPrevious;

    @ApiModelProperty(value = "是否有下一页")
    private Boolean hasNext;

    /**
     * 构造方法
     */
    public PageResult(Long current, Long size, Long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
        this.pages = (total + size - 1) / size; // 计算总页数
        this.hasPrevious = current > 1;
        this.hasNext = current < pages;
    }

    /**
     * 从 MyBatis Plus 的 Page 对象转换
     */
    public static <T> PageResult<T> of(Page<T> page) {
        return new PageResult<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                page.getRecords()
        );
    }

    /**
     * 创建空的分页结果
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>(1L, 10L, 0L, List.of());
    }

    /**
     * 创建空的分页结果（指定分页参数）
     */
    public static <T> PageResult<T> empty(PageRequest pageRequest) {
        return new PageResult<>((long) pageRequest.getPage(), (long) pageRequest.getSize(), 0L, List.of());
    }

    /**
     * 是否为空
     */
    public boolean isEmpty() {
        return records == null || records.isEmpty();
    }

    /**
     * 是否不为空
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }
}