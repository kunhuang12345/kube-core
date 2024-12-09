package com.hk.kubecore.service.impl;

import com.hk.kubecore.service.PodService;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

    private final CoreV1Api coreV1Api;

    @Override
    public List<V1Pod> getPodList(String namespace) {
        try {
            if (StringUtils.isNotEmpty(namespace)) {
                V1PodList podList = coreV1Api.listNamespacedPod(namespace).execute();
                return podList.getItems();
            }
            V1PodList podList = coreV1Api.listPodForAllNamespaces().execute();
            return podList.getItems();
        } catch (ApiException e) {
            throw new RuntimeException("无法获取pods", e);
        }
    }

    @Override
    public List<String> getNamespaceList() {
        try {
            List<V1Namespace> namespaceList = coreV1Api.listNamespace().execute().getItems();
            return namespaceList.stream()
                    .map(V1Namespace::getMetadata)
                    .filter(Objects::nonNull)
                    .map(V1ObjectMeta::getName)
                    .toList();
        } catch (ApiException e) {
            throw new RuntimeException("无法获取namespaces", e);
        }
    }
}
