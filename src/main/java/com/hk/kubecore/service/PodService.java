package com.hk.kubecore.service;

import com.hk.kubecore.entity.PodPo;
import com.hk.kubecore.entity.dto.PodDto;

import java.util.List;

public interface PodService {

    /**
     * 列出指定命名空间的Pod
     *
     * @param podPo 命名空间
     * @return Pod列表
     */
    List<PodDto> getPodList(PodPo podPo);

    /**
     * 获取命名空间列表
     *
     * @return 命名空间列表
     */
    List<String> getNamespaceList();
}
