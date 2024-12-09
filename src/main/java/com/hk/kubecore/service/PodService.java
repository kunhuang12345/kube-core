package com.hk.kubecore.service;

import io.kubernetes.client.openapi.models.V1Pod;

import java.util.List;

public interface PodService {

    /**
     * 列出指定命名空间的Pod
     *
     * @param namespace 命名空间
     * @return Pod列表
     */
    List<V1Pod> getPodList(String namespace);

    /**
     * 获取命名空间列表
     *
     * @return 命名空间列表
     */
    List<String> getNamespaceList();
}
