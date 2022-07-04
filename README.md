# soybean-admin-java

### 部署启动步骤

1. 依次进入soybean-dependencies/soybean-framework目录执行

```
mvn clean install
```

2. 进入deploy/env-build/docker目录执行

```
docker-compose -f dev/dev-docker-compose.yaml up -d
```

3. 将deploy/components/sql中的脚本导入数据库

```
需要注意uaa中的数据库连接地址为自己实际地址需要注意uaa中的数据库连接地址为自己实际地址
```

4. 依次启动`gateway`;`uaa`;`monitor`
5. 说明

```
1. 推荐安装portainer进行docker可视化管理
2. nacos: http://localhost:8848/nacos
3. skywalking: http://localhost:8080
4. elstichd: http://localhost:9800
5. sentinel: http://localhost:8858/#/login
6. grafana: http://localhost:3000
7. rabbit: http://localhost:15672
8. prometheus: http://localhost:9090
```
