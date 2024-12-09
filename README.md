### 一、项目整体架构

#### 1. 技术选型

- 前端：

  - React + Vite + TypeScript，用于构建前端页面和交互界面

  - UI 框架：Ant Design 或 Material-UI（方便快速构建交互界面）
  - 状态管理：Redux Toolkit 或 React Context
  - Axios：处理 API 请求

- 后端：

  - Spring Boot + Spring Web + Spring Data，用于实现 API 和逻辑
  - 数据库：MySQL，用于存储平台数据（例如容器记录、用户信息等）
  - k8s Java 客户端：通过 Java SDK 与 Kubernetes API 交互

- 部署：

  - K8s 集群用于容器管理
  - Nginx 或类似工具反向代理前后端

------

### 二、功能分析

你的系统需要实现以下核心功能：

1. 容器管理功能：
   - 查看容器（Pod）列表
   - 创建容器
   - 迁移容器
   - 停止/启动容器
   - 删除容器
2. 日志和监控功能：
   - 查看容器的实时日志
   - 查看资源使用情况（CPU、内存）
3. 用户管理（选做）：
   - 登录/注册
   - 用户权限控制（不同用户可以访问不同的容器）

------

### 三、前端设计

#### 1. 页面结构

以下是前端需要实现的页面及功能模块：

1. 登录页面：
   - 用户输入用户名、密码登录。
   - 登录成功后跳转到容器管理页面。
2. 容器管理页面：
   - 展示容器列表，包含以下信息：
     - 容器名称
     - 所属命名空间
     - 状态（Running/Terminated）
     - 节点位置
     - 创建时间
     - 操作按钮（查看详情、迁移、停止、删除）
   - 支持对容器的基本操作（创建、迁移、停止、删除）。
3. 容器详情页面：
   - 展示容器的详细信息：
     - 配置（CPU、内存、镜像等）
     - 状态
     - 当前日志
     - 资源使用情况（选做）
   - 支持实时查看日志。
4. 新增/编辑容器页面：
   - 表单页面，用于创建新容器，配置镜像、资源限制等。
5. 监控页面（选做）：
   - 展示 K8s 集群和容器的整体状态，例如 Pod 数量、CPU 使用情况、内存使用情况。

#### 2. 前端路由设计

可以使用 React Router，以下是路由结构：

- `/login` - 登录页面
- `/dashboard` - 仪表盘（容器管理页面）
- `/pod/:id` - 容器详情页面
- `/create` - 新增容器页面
- `/monitor` - 集群监控页面

------

### 四、后端设计

#### 1. API 设计

根据需求划分的主要 API 列表如下：

| **功能**     | **方法** | **路径**                   | **说明**                    |
| ------------ | -------- | -------------------------- | --------------------------- |
| 登录         | POST     | `/api/auth/login`          | 用户登录                    |
| 获取Pod列表  | GET      | `/api/pods`                | 获取所有Pod的信息           |
| 获取Pod详情  | GET      | `/api/pods/{name}`         | 根据Pod名称获取其详细信息   |
| 创建Pod      | POST     | `/api/pods`                | 创建新的Pod                 |
| 删除Pod      | DELETE   | `/api/pods/{name}`         | 删除指定Pod                 |
| 迁移Pod      | POST     | `/api/pods/{name}/migrate` | 将Pod迁移到指定节点         |
| 停止/启动Pod | POST     | `/api/pods/{name}/action`  | 启动或停止Pod               |
| 获取日志     | GET      | `/api/pods/{name}/logs`    | 获取Pod的实时日志           |
| 集群监控     | GET      | `/api/monitor`             | 获取CPU、内存等资源使用情况 |

#### 2. 数据库设计

假设你需要记录容器的操作日志和用户信息，数据库表设计如下：

1. **用户表 (users)**：
   - id (主键)
   - username
   - password (建议加密存储)
   - role (用户角色，例如 admin 或 user)
2. **容器操作日志表 (operation_logs)**：
   - id (主键)
   - pod_name
   - operation_type (例如 create、delete、migrate)
   - timestamp
   - operator (操作人)

#### 3. 后端逻辑实现

1. 使用 K8s Java 客户端：

   - 依赖库：`io.kubernetes:client-java`

   - 示例：

     ```java
     ApiClient client = Config.defaultClient();
     Configuration.setDefaultApiClient(client);
     CoreV1Api api = new CoreV1Api();
     V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
     ```

   - 操作 Pod（如创建、删除、迁移）时，直接调用对应 API 方法。

2. 日志功能：

   - 使用 Kubernetes API 获取实时日志：

     ```java
     String logs = api.readNamespacedPodLog(podName, namespace, null, null, null, null, null, null, null, null);
     ```

3. 迁移容器：

   - 删除旧 Pod，重新调度到新节点。

#### 4. Spring Boot 模块划分

- **Controller 层**：处理 HTTP 请求
- **Service 层**：封装业务逻辑
- **Repository 层**：数据库操作（如果有需要）
- **K8sClient**：与 K8s API 的交互工具类

------

### 五、开发步骤

#### 1. 环境准备

- 搭建 K8s 单节点集群（Minikube 或 Kind）
- 配置好 K8s API 的访问权限
- 初始化 React 项目和 Spring Boot 项目

#### 2. 开发顺序

1. 后端 API 开发：
   - 先实现 `/api/pods` 的 GET、POST、DELETE 接口
   - 测试与 K8s 的交互是否正常
2. 前端页面开发：
   - 先实现基本的容器列表展示（从后端获取数据并渲染）
   - 实现新增容器、删除容器的交互
3. 日志和监控功能：
   - 集成实时日志查看功能
   - 使用 Chart.js 或类似工具展示监控数据
4. 用户管理功能（选做）：
   - 添加登录页面和用户权限管理逻辑

------

### 六、测试与优化

1. 功能测试：
   - 测试容器的创建、迁移、删除等操作是否正常
   - 测试日志、监控功能的准确性
2. 性能优化：
   - 使用缓存减少对 K8s API 的频繁调用
   - 前端使用分页或虚拟滚动优化 Pod 列表渲染
3. 安全性：
   - 后端接口添加身份认证和权限校验
   - 前端做好表单校验，避免注入攻击

------

### 七、项目亮点提升建议

1. 支持 YAML 配置创建容器：
   - 提供一个编辑器页面，用户可以直接上传 YAML 文件创建 Pod。
2. 自动化部署：
   - 将前后端打包成 Docker 镜像，部署到 K8s 中，体现完整的 DevOps 流程。
3. 扩展功能：
   - 支持 StatefulSet、Deployment 的管理
   - 添加 WebSocket 实现实时日志推送
