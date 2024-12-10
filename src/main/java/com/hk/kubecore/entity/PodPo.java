package com.hk.kubecore.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class PodPo {
    @ApiModelProperty("命名空间")
    private final String namespace;
    @ApiModelProperty("控制返回结果的可读性")
    private String pretty;
    @ApiModelProperty("允许客户端通过watch参数来获取资源的变更情况")
    private Boolean allowWatchBookmarks;
    @ApiModelProperty("继续获取资源列表")
    private String _continue;
    @ApiModelProperty("根据特定字段的值过滤Pod资源:metadata.name=my-pod")
    private String fieldSelector;
    @ApiModelProperty("根据标签过滤Pod资源:app=nginx,environment=production")
    private String labelSelector;
    @ApiModelProperty("限制返回的资源数量")
    private Integer limit;
    @ApiModelProperty("返回资源的版本")
    private String resourceVersion;
    @ApiModelProperty("资源版本匹配模式")
    private String resourceVersionMatch;
    @ApiModelProperty("在监听操作中控制是否发送初始状态事件")
    private Boolean sendInitialEvents;
    @ApiModelProperty("超时时间")
    private Integer timeoutSeconds;
    @ApiModelProperty("是否监听")
    private Boolean watch;

}
