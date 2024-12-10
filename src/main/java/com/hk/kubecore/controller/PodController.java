package com.hk.kubecore.controller;

import com.hk.kubecore.entity.PodPo;
import com.hk.kubecore.entity.dto.PodDto;
import com.hk.kubecore.service.PodService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PodController {

    private final PodService podService;

    @GetMapping("/pod")
    @ApiOperation("获取Pod列表")
    public List<PodDto> getPodList(@ModelAttribute PodPo podPo) {
        return podService.getPodList(podPo);
    }

    @GetMapping("/namespaces")
    @ApiOperation("获取命名空间列表")
    public List<String> getNamespaceList() {
        return podService.getNamespaceList();
    }

}
