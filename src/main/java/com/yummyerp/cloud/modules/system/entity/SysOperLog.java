package com.yummyerp.cloud.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志记录表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_oper_log")
@ApiModel(value = "SysOperLog对象", description = "操作日志记录表")
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableField("id")
    @ApiModelProperty("日志主键")
    private Long id;

    /**
     * 模块标题
     */
    @TableField("title")
    @ApiModelProperty("模块标题")
    private String title;

    /**
     * 业务类型（0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据）
     */
    @TableField("business_type")
    @ApiModelProperty("业务类型（0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据）")
    private Integer businessType;

    /**
     * 方法名称
     */
    @TableField("method")
    @ApiModelProperty("方法名称")
    private String method;

    /**
     * 请求方式
     */
    @TableField("request_method")
    @ApiModelProperty("请求方式")
    private String requestMethod;

    /**
     * 操作类别（0=后台用户,1=手机端用户,2=其它）
     */
    @TableField("operator_type")
    @ApiModelProperty("操作类别（0=后台用户,1=手机端用户,2=其它）")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @TableField("oper_name")
    @ApiModelProperty("操作人员")
    private String operName;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 请求URL
     */
    @TableField("oper_url")
    @ApiModelProperty("请求URL")
    private String operUrl;

    /**
     * 主机地址
     */
    @TableField("oper_ip")
    @ApiModelProperty("主机地址")
    private String operIp;

    /**
     * 操作地点
     */
    @TableField("oper_location")
    @ApiModelProperty("操作地点")
    private String operLocation;

    /**
     * 请求参数
     */
    @TableField("oper_param")
    @ApiModelProperty("请求参数")
    private String operParam;

    /**
     * 返回参数
     */
    @TableField("json_result")
    @ApiModelProperty("返回参数")
    private String jsonResult;

    /**
     * 操作状态（0=正常,1=异常）
     */
    @TableField("status")
    @ApiModelProperty("操作状态（0=正常,1=异常）")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    @ApiModelProperty("错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField("oper_time")
    @ApiModelProperty("操作时间")
    private Date operTime;
    
    /**
     * 消耗时间(毫秒)
     */
    @TableField("cost_time")
    @ApiModelProperty("消耗时间(毫秒)")
    private Long costTime;
}