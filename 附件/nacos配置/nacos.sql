/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 22/08/2022 22:33:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info` VALUES (33, 'soybean-gateway.yml', 'DEFAULT_GROUP', 'spring:\n  cloud:\n    gateway:\n      # 动态路由\n      dynamic-route:\n        type: redis\n        enabled: true\n      enabled: true\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        - id: soybean-uaa\n          uri: lb://soybean-uaa\n          predicates:\n            - Path=/authority/**\n          filters:\n            - name: RequestRateLimiter\n              args:\n                redis-rate-limiter.replenishRate: 100   # 允许用户每秒处理多少个请求\n                redis-rate-limiter.burstCapacity: 100   # 令牌桶的容量，允许在一秒钟内完成的最大请求数\n                # 使用 IP 限流策略（使用 SpEL 按名称引用 bean）\n                key-resolver: \"#{@ipKeyResolver}\"\n            - StripPrefix=1\n            - name: Retry\n              args:\n                retries: 1\n                statuses: BAD_GATEWAY\n                backoff:\n                  firstBackoff: 200ms\n                  maxBackoff: 500ms\n                  factor: 1\n                  basedOnPreviousValue: false\n                  exceptions: TimeoutException\n      loadbalancer:\n        use404: true\n      httpclient:\n        response-timeout: 300s\n        pool:\n          type: elastic\n          max-idle-time: 30s\n          max-connections: 1000\n          acquire-timeout: 180000\n    discovery:\n      # 开启默认为 reactive 模式，需显示关闭可调整为阻塞模式\n      reactive:\n        enabled: true\n\nmanagement:\n  health:\n    db:\n      enabled: false\n\nribbon:\n  ReadTimeout: 5000000\n  ConnectTimeout: 5000000\n  MaxAutoRetries: 0\n  MaxAutoRetriesNextServer: 1', '3159516fd796c072d6e4719b1a6f742c', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', '', 'dev', '', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (34, 'soybean-uaa.properties', 'DEFAULT_GROUP', 'extend.redis.lock.enabled=true\nextend.mybatis-plus.multi-tenant.strategy=local\nextend.mybatis-plus.multi-tenant.type=datasource\n\nmanagement.endpoints.web.exposure.include=*\n\nsecurity.oauth2.client.client-id=authority\nsecurity.oauth2.client.ignore.resource-urls[0]=/actuator/**\nsecurity.oauth2.client.ignore.resource-urls[1]=/oauth/**\nsecurity.oauth2.client.ignore.resource-urls[2]=/favicon.ico\nsecurity.oauth2.client.ignore.resource-urls[3]=/message/**\nsecurity.oauth2.client.ignore.resource-urls[4]=/instances/**\nsecurity.oauth2.client.ignore.resource-urls[5]=/', '5086a736dcaa81f2d871a4a089c365f6', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', '', 'dev', NULL, NULL, NULL, 'properties', NULL, '');
INSERT INTO `config_info` VALUES (35, 'soybean-datasource.properties', 'DEFAULT_GROUP', 'spring.datasource.dynamic.primary=master\nspring.datasource.dynamic.lazy=true\nspring.datasource.dynamic.hikari.keepalive-time=120000\nspring.datasource.dynamic.datasource.master.pool-name=HikariDataSourcePool\nspring.datasource.dynamic.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.datasource.dynamic.datasource.master.url=jdbc:mysql://120.48.68.52:32448/soybean-admin-local?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true\nspring.datasource.dynamic.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.datasource.dynamic.datasource.master.username=root\nspring.datasource.dynamic.datasource.master.password=dev-project@mysql.\nspring.datasource.dynamic.hikari.max-pool-size=15\nspring.datasource.dynamic.hikari.max-lifetime=1800000\nspring.datasource.dynamic.hikari.connection-timeout=60000\nspring.datasource.dynamic.hikari.min-idle=5\nspring.datasource.dynamic.hikari.is-auto-commit=true', '7cb35f86a4aca69043102d689f6524d1', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', '', 'dev', '', NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (36, 'soybean-redis.properties', 'DEFAULT_GROUP', 'spring.redis.database=2\n#spring.cache.type=redis\n#spring.cache.redis.cache-null-values=false\ns#pring.cache.redis.time-to-live=2H', '89ece49ce4fe5ac8de9f407d077cda83', '2022-08-22 22:07:45', '2022-08-22 22:08:49', 'nacos', '172.18.0.1', '', 'dev', '', '', '', 'properties', '', '');
INSERT INTO `config_info` VALUES (37, 'soybean-rabbitmq.properties', 'DEFAULT_GROUP', 'spring.rabbitmq.username=root\nspring.rabbitmq.password=123456', 'ba239d57271d8f037787e3e373b58789', '2022-08-22 22:07:45', '2022-08-22 22:10:34', 'nacos', '172.18.0.1', '', 'dev', '', '', '', 'properties', '', '');
INSERT INTO `config_info` VALUES (38, 'soybean-demo.properties', 'DEFAULT_GROUP', 'logging.level.com.soundforce=debug\n\nextend.mybatis-plus.multi-tenant.strategy=feign\n\nmanagement.endpoints.web.exposure.include=*\nmanagement.health.rabbit.enabled=false\n\nspring.cloud.bus.enabled=false\n\nsecurity.oauth2.client.client-id=demo\nsecurity.oauth2.client.client-secret=demo\nsecurity.oauth2.client.scope=server\nsecurity.oauth2.client.grant-type=client_credentials,password\nsecurity.oauth2.client.access-token-uri=http://soybean-uaa/oauth/token\nsecurity.oauth2.client.ignore.resource-urls[0]=/oauth/**\nsecurity.oauth2.client.ignore.resource-urls[1]=/actuator/**\nsecurity.oauth2.client.ignore.resource-urls[2]=/favicon.ico\nsecurity.oauth2.resource.loadBalanced=true\nsecurity.oauth2.resource.token-info-uri=http://soybean-uaa/oauth/check_token\nsecurity.oauth2.resource.prefer-token-info=false\nsecurity.oauth2.resource.user-info-uri=http://soybean-uaa/oauth/users', 'db9a53f921d65af1c4dc926cd5672a2f', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', '', 'dev', '', NULL, NULL, 'properties', NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='增加租户字段';

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info_beta';

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info_tag';

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_tag_relation';

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
BEGIN;
INSERT INTO `his_config_info` VALUES (0, 71, 'soybean-gateway.yml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    gateway:\n      # 动态路由\n      dynamic-route:\n        type: redis\n        enabled: true\n      enabled: true\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        - id: soybean-uaa\n          uri: lb://soybean-uaa\n          predicates:\n            - Path=/authority/**\n          filters:\n            - name: RequestRateLimiter\n              args:\n                redis-rate-limiter.replenishRate: 100   # 允许用户每秒处理多少个请求\n                redis-rate-limiter.burstCapacity: 100   # 令牌桶的容量，允许在一秒钟内完成的最大请求数\n                # 使用 IP 限流策略（使用 SpEL 按名称引用 bean）\n                key-resolver: \"#{@ipKeyResolver}\"\n            - StripPrefix=1\n            - name: Retry\n              args:\n                retries: 1\n                statuses: BAD_GATEWAY\n                backoff:\n                  firstBackoff: 200ms\n                  maxBackoff: 500ms\n                  factor: 1\n                  basedOnPreviousValue: false\n                  exceptions: TimeoutException\n      loadbalancer:\n        use404: true\n      httpclient:\n        response-timeout: 300s\n        pool:\n          type: elastic\n          max-idle-time: 30s\n          max-connections: 1000\n          acquire-timeout: 180000\n    discovery:\n      # 开启默认为 reactive 模式，需显示关闭可调整为阻塞模式\n      reactive:\n        enabled: true\n\nmanagement:\n  health:\n    db:\n      enabled: false\n\nribbon:\n  ReadTimeout: 5000000\n  ConnectTimeout: 5000000\n  MaxAutoRetries: 0\n  MaxAutoRetriesNextServer: 1', '3159516fd796c072d6e4719b1a6f742c', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', 'I', 'dev', '');
INSERT INTO `his_config_info` VALUES (0, 72, 'soybean-uaa.properties', 'DEFAULT_GROUP', '', 'extend.redis.lock.enabled=true\nextend.mybatis-plus.multi-tenant.strategy=local\nextend.mybatis-plus.multi-tenant.type=datasource\n\nmanagement.endpoints.web.exposure.include=*\n\nsecurity.oauth2.client.client-id=authority\nsecurity.oauth2.client.ignore.resource-urls[0]=/actuator/**\nsecurity.oauth2.client.ignore.resource-urls[1]=/oauth/**\nsecurity.oauth2.client.ignore.resource-urls[2]=/favicon.ico\nsecurity.oauth2.client.ignore.resource-urls[3]=/message/**\nsecurity.oauth2.client.ignore.resource-urls[4]=/instances/**\nsecurity.oauth2.client.ignore.resource-urls[5]=/', '5086a736dcaa81f2d871a4a089c365f6', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', 'I', 'dev', '');
INSERT INTO `his_config_info` VALUES (0, 73, 'soybean-datasource.properties', 'DEFAULT_GROUP', '', 'spring.datasource.dynamic.primary=master\nspring.datasource.dynamic.lazy=true\nspring.datasource.dynamic.hikari.keepalive-time=120000\nspring.datasource.dynamic.datasource.master.pool-name=HikariDataSourcePool\nspring.datasource.dynamic.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.datasource.dynamic.datasource.master.url=jdbc:mysql://120.48.68.52:32448/soybean-admin-local?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true\nspring.datasource.dynamic.datasource.master.type=com.zaxxer.hikari.HikariDataSource\nspring.datasource.dynamic.datasource.master.username=root\nspring.datasource.dynamic.datasource.master.password=dev-project@mysql.\nspring.datasource.dynamic.hikari.max-pool-size=15\nspring.datasource.dynamic.hikari.max-lifetime=1800000\nspring.datasource.dynamic.hikari.connection-timeout=60000\nspring.datasource.dynamic.hikari.min-idle=5\nspring.datasource.dynamic.hikari.is-auto-commit=true', '7cb35f86a4aca69043102d689f6524d1', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', 'I', 'dev', '');
INSERT INTO `his_config_info` VALUES (0, 74, 'soybean-redis.properties', 'DEFAULT_GROUP', '', 'spring.redis.host=120.48.68.52\nspring.redis.port=30243\nspring.redis.database=2\nspring.redis.password=dev-project@redis.\n#spring.cache.type=redis\n#spring.cache.redis.cache-null-values=false\ns#pring.cache.redis.time-to-live=2H', 'b1c00ecb85f7d55ba99ae2c5d6cf3376', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', 'I', 'dev', '');
INSERT INTO `his_config_info` VALUES (0, 75, 'soybean-rabbitmq.properties', 'DEFAULT_GROUP', '', 'spring.rabbitmq.host=120.48.68.52\nspring.rabbitmq.port=30600\nspring.rabbitmq.username=root\nspring.rabbitmq.password=dev-project@rabbitmq.', 'b20118047cc7844484fff9d58b37f870', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', 'I', 'dev', '');
INSERT INTO `his_config_info` VALUES (0, 76, 'soybean-demo.properties', 'DEFAULT_GROUP', '', 'logging.level.com.soundforce=debug\n\nextend.mybatis-plus.multi-tenant.strategy=feign\n\nmanagement.endpoints.web.exposure.include=*\nmanagement.health.rabbit.enabled=false\n\nspring.cloud.bus.enabled=false\n\nsecurity.oauth2.client.client-id=demo\nsecurity.oauth2.client.client-secret=demo\nsecurity.oauth2.client.scope=server\nsecurity.oauth2.client.grant-type=client_credentials,password\nsecurity.oauth2.client.access-token-uri=http://soybean-uaa/oauth/token\nsecurity.oauth2.client.ignore.resource-urls[0]=/oauth/**\nsecurity.oauth2.client.ignore.resource-urls[1]=/actuator/**\nsecurity.oauth2.client.ignore.resource-urls[2]=/favicon.ico\nsecurity.oauth2.resource.loadBalanced=true\nsecurity.oauth2.resource.token-info-uri=http://soybean-uaa/oauth/check_token\nsecurity.oauth2.resource.prefer-token-info=false\nsecurity.oauth2.resource.user-info-uri=http://soybean-uaa/oauth/users', 'db9a53f921d65af1c4dc926cd5672a2f', '2022-08-22 22:07:45', '2022-08-22 22:07:45', NULL, '172.18.0.1', 'I', 'dev', '');
INSERT INTO `his_config_info` VALUES (36, 77, 'soybean-redis.properties', 'DEFAULT_GROUP', '', 'spring.redis.host=120.48.68.52\nspring.redis.port=30243\nspring.redis.database=2\nspring.redis.password=dev-project@redis.\n#spring.cache.type=redis\n#spring.cache.redis.cache-null-values=false\ns#pring.cache.redis.time-to-live=2H', 'b1c00ecb85f7d55ba99ae2c5d6cf3376', '2022-08-22 22:08:49', '2022-08-22 22:08:49', 'nacos', '172.18.0.1', 'U', 'dev', '');
INSERT INTO `his_config_info` VALUES (37, 78, 'soybean-rabbitmq.properties', 'DEFAULT_GROUP', '', 'spring.rabbitmq.host=120.48.68.52\nspring.rabbitmq.port=30600\nspring.rabbitmq.username=root\nspring.rabbitmq.password=dev-project@rabbitmq.', 'b20118047cc7844484fff9d58b37f870', '2022-08-22 22:10:33', '2022-08-22 22:10:34', 'nacos', '172.18.0.1', 'U', 'dev', '');
COMMIT;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(255) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='租户容量信息表';

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
INSERT INTO `tenant_info` VALUES (1, '1', 'dev', 'dev', '开发环境', 'nacos', 1653760266220, 1653760298421);
INSERT INTO `tenant_info` VALUES (2, '1', 'docker', 'docker', 'docker测试环境', 'nacos', 1653760274390, 1653760308339);
INSERT INTO `tenant_info` VALUES (3, '1', 'test', 'test', '测试环境', 'nacos', 1653760283724, 1653760314411);
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
