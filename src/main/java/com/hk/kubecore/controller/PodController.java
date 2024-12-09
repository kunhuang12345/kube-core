package com.hk.kubecore.controller;

import com.hk.kubecore.service.PodService;
import io.kubernetes.client.openapi.models.V1Pod;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PodController {

    private final PodService podService;

    @GetMapping("/pods")
    @ApiOperation("获取Pod列表")
    public List<V1Pod> getPodList(@RequestParam(required = false) String namespace) {
        return podService.getPodList(namespace);
    }

    @GetMapping("/namespaces")
    @ApiOperation("获取命名空间列表")
    public List<String> getNamespaceList() {
        return podService.getNamespaceList();
    }

}
