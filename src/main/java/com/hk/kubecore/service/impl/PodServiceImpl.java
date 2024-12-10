package com.hk.kubecore.service.impl;

import com.hk.kubecore.entity.PodPo;
import com.hk.kubecore.entity.dto.PodDto;
import com.hk.kubecore.service.PodService;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

    private final CoreV1Api coreV1Api;

    @Override
    public List<PodDto> getPodList(PodPo podPo) {
        // TODO 通过条件获取列表
        try {
            if (StringUtils.isNotEmpty(podPo.getNamespace())) {
                V1PodList v1PodList = coreV1Api.listNamespacedPod(podPo.getNamespace())
                        .pretty(podPo.getPretty())
                        .allowWatchBookmarks(podPo.getAllowWatchBookmarks())
                        ._continue(podPo.get_continue())
                        .fieldSelector(podPo.getFieldSelector())
                        .labelSelector(podPo.getLabelSelector())
                        .limit(podPo.getLimit())
                        .resourceVersion(podPo.getResourceVersion())
                        .resourceVersionMatch(podPo.getResourceVersionMatch())
                        .sendInitialEvents(podPo.getSendInitialEvents())
                        .timeoutSeconds(podPo.getTimeoutSeconds())
                        .watch(podPo.getWatch())
                        .execute();
                List<V1Pod> items = v1PodList.getItems();
                return new ArrayList<>();
            }
            V1PodList v1PodList = coreV1Api.listPodForAllNamespaces()
                    .pretty(podPo.getPretty())
                    .allowWatchBookmarks(podPo.getAllowWatchBookmarks())
                    ._continue(podPo.get_continue())
                    .fieldSelector(podPo.getFieldSelector())
                    .labelSelector(podPo.getLabelSelector())
                    .limit(podPo.getLimit())
                    .resourceVersion(podPo.getResourceVersion())
                    .resourceVersionMatch(podPo.getResourceVersionMatch())
                    .sendInitialEvents(podPo.getSendInitialEvents())
                    .timeoutSeconds(podPo.getTimeoutSeconds())
                    .watch(podPo.getWatch())
                    .execute();
            List<V1Pod> items = v1PodList.getItems();
            List<PodDto> podDtoList = new ArrayList<>();
            for (V1Pod v1Pod : items) {
                PodDto podDto = new PodDto();
                V1ObjectMeta metadata = v1Pod.getMetadata();
                if (metadata != null) {
                    BeanUtils.copyProperties(metadata, podDto);
                    podDtoList.add(podDto);
                }
            }
            return podDtoList;
        } catch (Exception e) {
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
