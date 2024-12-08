package com.hk.kubecore.controller;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final CoreV1Api coreV1Api;

    @GetMapping("/pods")
    public List<V1Pod> listPods() {
        try {
            V1PodList podList = coreV1Api.listPodForAllNamespaces().execute();
            return podList.getItems();
        } catch (ApiException e) {
            throw new RuntimeException("Failed to list pods", e);
        }
    }

}
