# SoybeanAdmin Quarkus

<p align="center">
  <a href="https://github.com/soybeanjs/soybean-admin-quarkus/blob/main/LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="license"/>
  </a>
  <a href="https://github.com/soybeanjs/soybean-admin-quarkus/stargazers">
    <img src="https://img.shields.io/github/stars/soybeanjs/soybean-admin-quarkus.svg" alt="stars"/>
  </a>
  <a href="https://github.com/soybeanjs/soybean-admin-quarkus/network/members">
    <img src="https://img.shields.io/github/forks/soybeanjs/soybean-admin-quarkus.svg" alt="forks"/>
  </a>
  <a href="https://github.com/soybeanjs/soybean-admin-quarkus/issues">
    <img src="https://img.shields.io/github/issues/soybeanjs/soybean-admin-quarkus.svg" alt="issues"/>
  </a>
</p>

<p align="center">
  <a href="#简介">简介</a> •
  <a href="#前端项目">前端项目</a> •
  <a href="#特性">特性</a> •
  <a href="#项目结构">项目结构</a> •
  <a href="#快速开始">快速开始</a> •
  <a href="#技术栈">技术栈</a> •
  <a href="#贡献指南">贡献指南</a> •
  <a href="#许可证">许可证</a>
</p>

## 简介

SoybeanAdmin Quarkus 是一个基于 Kotlin 和 Quarkus 的现代化后台管理系统脚手架。它融合了领域驱动设计（DDD）、命令查询责任分离（CQRS）和事件溯源等先进的软件架构概念。本项目采用 Gradle 构建，旨在为开发者提供一个轻量级、高性能且易于扩展的管理系统开发框架。

我们的目标是为开发者提供一个灵活、模块化的起点，内置基础的权限管理功能，以便快速构建高质量的企业级管理系统。借助 Quarkus 框架的优势，SoybeanAdmin Quarkus 实现了快速启动、低内存占用和卓越的性能表现。

无论您是开发小型管理后台，还是构建 CMS 等企业应用，SoybeanAdmin Quarkus 都能为您提供坚实的基础和高效的开发体验。本项目提供了灵活可扩展的架构，您可以根据具体需求和偏好进行定制和扩展。

## 前端项目

本项目的前端部分基于 [SoybeanAdmin](https://github.com/soybeanjs/soybean-admin) 进行二次开发。SoybeanAdmin 是一个现代化的前端管理系统模板，提供了丰富的UI组件和功能模块，与本后端项目完美配合，共同构建完整的全栈管理系统解决方案。

前端项目地址：[https://github.com/ByteByteBrew/soybean-admin](https://github.com/ByteByteBrew/soybean-admin)

## 特性

- **Kotlin 语言**：利用 Kotlin 的简洁性和表现力，提供安全、简洁的代码。
- **Quarkus 框架**：采用 Quarkus 框架，实现快速启动、低内存占用和卓越性能。
- **DDD 设计**：应用领域驱动设计原则，提供清晰的业务逻辑分层和领域模型。
- **CQRS 模式**：实现命令查询责任分离，优化系统的读写性能和可扩展性。
- **事件溯源**：通过事件溯源模式，实现系统状态的可追溯性和可重现性。
- **Gradle 构建**：使用 Gradle 构建工具，简化项目构建和依赖管理流程。
- **模块化设计**：采用模块化设计，实现高内聚、低耦合的代码组织结构。
- **自动化路由**：简化 API 端点的管理，提高开发效率。
- **权限管理**：内置基于角色的访问控制（RBAC）系统，保障系统安全。
- **JWT 认证**：实现安全可靠的用户认证和授权机制。
- **API 文档**：自动生成 Swagger API 文档，便于接口管理和测试。
- **环境配置**：支持多环境配置，满足不同部署场景的需求。
- **前后端分离**：采用前后端分离架构，提高开发效率和系统可维护性。

## 项目结构

```
soybean-admin-quarkus/
├── domain/                                 # 领域模块
│   └── src/main/kotlin/cn/soybean/domain/
│       ├── base/                           # 基础实体
│       │   ├── BaseEntity.kt               # 基础实体类
│       │   └── BaseTenantEntity.kt         # 基础租户实体类
│       └── system/                         # 系统领域
│           ├── aggregate/                  # 聚合根
│           ├── config/                     # 配置
│           ├── entity/                     # 实体
│           ├── enums/                      # 枚举
│           ├── event/                      # 领域事件
│           ├── repository/                 # 仓储接口
│           └── vo/                         # 值对象
├── shared/                                 # 共享模块
│   └── src/main/kotlin/cn/soybean/shared/
│       ├── application/                    # 共享的应用服务
│       ├── domain/                         # 共享的领域逻辑
│       ├── eventsourcing/                  # 事件总线
│       ├── infrastructure/                 # 共享的基础设施
│       ├── projection/                     # 共享的投影
│       └── util/                           # 共享的工具类
└── system/                                 # 系统模块
    └── src/main/kotlin/cn/soybean/
        ├── application/                    # 应用层
        │   ├── command/                    # 命令处理
        │   ├── exceptions/                 # 应用层异常
        │   └── query/                      # 查询处理
        ├── domain/                         # 领域层
        │   ├── aggregate/                  # 聚合根
        │   └── event/                      # 领域事件
        ├── eventsourcing/                  # 事件溯源
        │   ├── convert/                    # 事件转换
        │   └── entity/                     # 事件实体
        ├── infrastructure/                 # 基础设施层
        │   ├── config/                     # 配置
        │   ├── interceptor/                # 拦截器
        │   ├── persistence/                # 持久化
        │   └── security/                   # 安全相关
        ├── interfaces/rest/                # REST接口
        │   ├── dto/                        # 数据传输对象
        │   ├── exceptions/                 # 接口异常
        │   └── response/                   # 响应封装
        ├── projection/                     # 投影
        └── system/                         # 系统核心
            ├── application/                # 系统应用服务
            │   ├── bootstrap/              # 启动引导
            │   ├── command/                # 命令处理
            │   ├── convert/                # 数据转换
            │   ├── event/                  # 事件处理
            │   ├── eventhandler/           # 事件处理器
            │   ├── query/                  # 查询处理
            │   └── service/                # 服务实现
            └── infrastructure/             # 系统基础设施
                ├── localization/           # 本地化
                ├── persistence/            # 持久化实现
                ├── security/               # 安全实现
                ├── util/                   # 工具类
                └── web/                    # Web相关
            └── interfaces/rest/            # 路由
            └── projection/                 # 投影实现
```

## 快速开始

1. 克隆项目

```bash
git clone https://github.com/soybeanjs/soybean-admin-quarkus.git
cd soybean-admin-quarkus
```

2. 构建项目

```bash
./gradlew build
```

3. 运行项目

```bash
./gradlew quarkusDev
```

4. 访问 API 文档

打开浏览器，访问 `http://localhost:8080/q/swagger-ui`

## 技术栈

- Kotlin：主要编程语言
- Quarkus：应用框架
- Gradle：项目构建工具
- Hibernate ORM with Panache：ORM 框架
- RESTEasy：RESTful Web 服务框架
- Swagger UI：API 文档生成工具
- JWT：身份认证机制
- PostgreSQL：默认数据库（可根据需求更换）

## 贡献指南

我们欢迎并感谢所有形式的贡献，包括新功能、bug 修复、文档改进等。如果您想为项目做出贡献，请遵循以下步骤：

1. 在 GitHub 上 Fork 本仓库
2. 从您的 Fork 中克隆项目到本地
3. 创建新的分支以进行更改 (`git checkout -b feature/YourFeatureName`)
4. 提交您的更改 (`git commit -m 'Add some feature'`)
5. 将更改推送到您的 Fork (`git push origin feature/YourFeatureName`)
6. 在 GitHub 上从您的 Fork 创建一个新的 Pull Request

请确保您的代码符合项目的编码规范，并附带适当的测试用例。

## 许可证

本项目采用 MIT 许可证。有关详细信息，请查看 [LICENSE](LICENSE) 文件。

---

如果您在使用过程中遇到任何问题或有任何建议，欢迎提出 Issue 或 Pull Request。我们期待您的反馈和贡献，共同改进 SoybeanAdmin Quarkus 项目！
