<div align="center">
	<img src="https://i.loli.net/2021/11/24/x5lLfuSnEawBAgi.png"/>
	<h1>Soybean Admin Java</h1>
</div>

[![Spring Boot](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-dependencies.svg?label=Spring%20Boot&logo=Spring)](https://search.maven.org/artifact/org.springframework.boot/spring-boot-dependencies)  [![Spring Cloud](https://img.shields.io/maven-central/v/org.springframework.cloud/spring-cloud-dependencies.svg?label=Spring%20Cloud&logo=Spring)](https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-dependencies)  [![Spring Cloud Alibaba](https://img.shields.io/maven-central/v/com.alibaba.cloud/spring-cloud-alibaba-dependencies.svg?label=Spring%20Cloud%20Alibaba&logo=Spring)](https://search.maven.org/artifact/com.alibaba.cloud/spring-cloud-alibaba-dependencies)  [![Docker](https://img.shields.io/badge/Docker-success)]()  [![docker--compose](https://img.shields.io/badge/docker--compose-success)]()  [![Portainer](https://img.shields.io/badge/Portainer-success)]()  [![skywalking](https://img.shields.io/badge/skywalking-inactive)]()  [![elasticsearch](https://img.shields.io/badge/elasticsearch-important)]()  [![fluentd](https://img.shields.io/badge/fluentd-important)]()  [![kibana](https://img.shields.io/badge/kibana-important)]()  [![grafana](https://img.shields.io/badge/grafana-ff69b4)]()  [![prometheus](https://img.shields.io/badge/prometheus-ff69b4)]()  [![loki](https://img.shields.io/badge/loki-ff69b4)]()  
## 简介

Soybean Admin Java 是一个基于 Spring-Boot、Spring-Cloud、Spring-Cloud-Alibaba、Nacos、Mybatis-Plus、Oauth2.0、Redis、RabbitMq、Mysql等基本组件开发的开箱即用Sass微服务架构平台

## 说明

- 目前功能暂时只有基本租户sass基础能力，如需新功能请提ISSUE
- 当前开源中台众多，项目初衷不为在众多开源项目中脱颖而出，旨在传统java开发者对云原生环境有整体的认知；以及对于各种中间件的适用场景和使用方式

## 项目示例

![](http://120.48.68.52:30385/default/img/iShot_2022-08-22_22.36.48.png)

![](http://120.48.68.52:30385/default/img/iShot_2022-08-22_22.40.07.png)

![](http://120.48.68.52:30385/default/img/iShot_2022-08-22_22.44.07.png)

![](http://120.48.68.52:30385/default/img/iShot_2022-08-22_22.47.13.png)

![](http://120.48.68.52:30385/default/img/iShot_2022-08-22_22.52.45.png)

![](http://120.48.68.52:30385/default/img/iShot_2022-08-22_22.55.37.png)

## 开发环境部署
- 先决条件
  [![Docker](https://img.shields.io/badge/Docker-success)]()  [![docker--compose](https://img.shields.io/badge/docker--compose-success)]()
  ```
	# 推荐安装portainer作为本地docker可视化管理平台
	$ docker volume create portainer_data
	$ docker run -d -p 8000:8000 -p 9443:9443 --name portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce:2.14.2

	# 首先创建开发环境网络隔离
	$ docker network create dev

	# 进入deploy/env-build/docker/dev/fluentd目录下运行
	$ docker build -t fluent/fluentd:me .

	# 进入deploy/env-build/docker/dev目录下运行
	$ docker-compose up -d

	tips: 静等以上镜像容器创建完毕,用时可能会很长,建议去打一把游戏

	# 以上镜像安装完有两点问题
	1 mysql不能执行初始化脚本,解决方案先手动将附件中的nacos.sql和soybean-admin-local.sql手动执行下
	2 docker-compose.yaml中的dev-web需要引用fluentd,案例中的172.18.0.12地址需要根据实际docker中fluentd在dev网络中的地址进行修改[linux环境不需要修改,大概知道问题原因,开发环境暂时按照此方案手动处理],修改后再次执行docker-compose up -d

	tips: 如果一路顺利在Portainer控制台中所有容器应该是全部启动且没有报错的状态

	以上开发环境搭建完成后按以下步骤启动
	1. 进入soybean-dependencies指定maven clean install(idea maven控制台也可以)
	2. 进入soybean-framework指定maven clean install
	3. 依次编译打包并启动soybean-gateway,soybean-uaa,soybean-monitor,soybean-demo
  ```

- 前端建设中
