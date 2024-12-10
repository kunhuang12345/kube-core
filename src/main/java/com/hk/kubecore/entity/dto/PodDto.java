package com.hk.kubecore.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PodDto {
    private String name;
    private String namespace;
    private String status;
    private String nodeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;
    @ApiModelProperty("分页标记")
    private String _continue;
}
