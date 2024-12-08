package com.hk.kubecore.config;


import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class KubernetesConfig {

    /**
     * 配置并返回 Kubernetes ApiClient Bean
     *
     * @return 配置好的 ApiClient 实例
     * @throws IOException 如果读取 kubeconfig 文件失败
     */
    @Bean
    public ApiClient apiClient() throws IOException {
        // 获取 kubeconfig 文件路径
        String kubeConfigPath = System.getProperty("user.home") + "/.kube/config";

        // 构建 ApiClient 实例
        ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath)))
                .build();

        // 设置默认 ApiClient
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);

        return client;
    }

    /**
     * 配置并返回 CoreV1Api Bean
     *
     * @param apiClient 注入的 ApiClient 实例
     * @return 配置好的 CoreV1Api 实例
     */
    @Bean
    public CoreV1Api coreV1Api(ApiClient apiClient) {
        return new CoreV1Api(apiClient);
    }
}
