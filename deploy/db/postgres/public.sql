/*
 Navicat Premium Dump SQL

 Source Server         : @localhost-5432
 Source Server Type    : PostgreSQL
 Source Server Version : 170001 (170001)
 Source Host           : localhost:5432
 Source Catalog        : soybean-admin-backend
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 170001 (170001)
 File Encoding         : 65001

 Date: 22/11/2024 11:42:22
*/


-- ----------------------------
-- Sequence structure for sys_operation_log_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_operation_log_seq";
CREATE SEQUENCE "public"."sys_operation_log_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."sys_operation_log_seq" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_api_key
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_api_key";
CREATE TABLE "public"."sys_api_key" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "api_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "api_secret" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2,
  "type" int2,
  "used_count" int8
)
;
ALTER TABLE "public"."sys_api_key" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_apis
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_apis";
CREATE TABLE "public"."sys_apis" (
  "operation_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "method" varchar(20) COLLATE "pg_catalog"."default",
  "inclusive" bool,
  "path" varchar(64) COLLATE "pg_catalog"."default",
  "permissions" varchar(64) COLLATE "pg_catalog"."default",
  "summary" varchar(64) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."sys_apis" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept";
CREATE TABLE "public"."sys_dept" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "leader_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "leader_user_id" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "sequence" int4,
  "parent_id" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."sys_dept" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_data";
CREATE TABLE "public"."sys_dict_data" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "color" varchar(20) COLLATE "pg_catalog"."default",
  "dict_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "label" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "sequence" int4 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int2,
  "type" varchar(20) COLLATE "pg_catalog"."default",
  "value" varchar(20) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_dict_data" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_type";
CREATE TABLE "public"."sys_dict_type" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "dict_name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int2
)
;
ALTER TABLE "public"."sys_dict_type" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_login_log";
CREATE TABLE "public"."sys_login_log" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "account_name" varchar(20) COLLATE "pg_catalog"."default",
  "action" varchar(20) COLLATE "pg_catalog"."default",
  "login_ip" varchar(20) COLLATE "pg_catalog"."default",
  "login_port" int4,
  "login_region" varchar(64) COLLATE "pg_catalog"."default",
  "login_type" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "user_agent" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."sys_login_log" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "active_menu" varchar(64) COLLATE "pg_catalog"."default",
  "built_in" bool NOT NULL,
  "component" varchar(64) COLLATE "pg_catalog"."default",
  "constant" bool,
  "hide_in_menu" bool,
  "href" varchar(64) COLLATE "pg_catalog"."default",
  "i18n_key" varchar(64) COLLATE "pg_catalog"."default",
  "icon" varchar(64) COLLATE "pg_catalog"."default",
  "icon_type" varchar(20) COLLATE "pg_catalog"."default",
  "keep_alive" bool,
  "menu_name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type" int2 NOT NULL,
  "multi_tab" bool,
  "sequence" int4 NOT NULL,
  "parent_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "roles" varchar(255) COLLATE "pg_catalog"."default",
  "route_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "route_path" varchar(128) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL
)
;
ALTER TABLE "public"."sys_menu" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_operation_log";
CREATE TABLE "public"."sys_operation_log" (
  "id" int8 NOT NULL,
  "account_name" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "duration" varchar(255) COLLATE "pg_catalog"."default",
  "method" varchar(255) COLLATE "pg_catalog"."default",
  "module_name" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(255) COLLATE "pg_catalog"."default",
  "request_info" varchar(255) COLLATE "pg_catalog"."default",
  "resource" varchar(255) COLLATE "pg_catalog"."default",
  "result_code" int4,
  "result_data" varchar(255) COLLATE "pg_catalog"."default",
  "result_msg" varchar(255) COLLATE "pg_catalog"."default",
  "start_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default",
  "trace_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_agent" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_ip" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."sys_operation_log" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_post";
CREATE TABLE "public"."sys_post" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "sequence" int4,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int2
)
;
ALTER TABLE "public"."sys_post" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "built_in" bool NOT NULL,
  "code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "data_scope" int2,
  "data_scope_dept_ids" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "sequence" int4 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL
)
;
ALTER TABLE "public"."sys_role" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_role_api
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_api";
CREATE TABLE "public"."sys_role_api" (
  "operation_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_role_api" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_menu";
CREATE TABLE "public"."sys_role_menu" (
  "menu_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_role_menu" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_user";
CREATE TABLE "public"."sys_role_user" (
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_role_user" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_tenant";
CREATE TABLE "public"."sys_tenant" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "built_in" bool NOT NULL,
  "contact_account_name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "contact_user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "expire_time" timestamp(6) NOT NULL,
  "menu_ids" varchar(2048) COLLATE "pg_catalog"."default",
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_ids" varchar(2048) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL,
  "website" varchar(64) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."sys_tenant" OWNER TO "soybean";

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_account_name" varchar(20) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "tenant_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "account_name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "account_password" varchar(255) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "built_in" bool NOT NULL,
  "country_code" varchar(5) COLLATE "pg_catalog"."default",
  "dept_id" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(64) COLLATE "pg_catalog"."default",
  "gender" int2,
  "nick_name" varchar(20) COLLATE "pg_catalog"."default",
  "personal_profile" varchar(255) COLLATE "pg_catalog"."default",
  "phone_code" varchar(5) COLLATE "pg_catalog"."default",
  "phone_number" varchar(20) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL
)
;
ALTER TABLE "public"."sys_user" OWNER TO "soybean";

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."sys_operation_log_seq"', 1, false);

-- ----------------------------
-- Uniques structure for table sys_api_key
-- ----------------------------
ALTER TABLE "public"."sys_api_key" ADD CONSTRAINT "uk5jqsvjvy48394hg6fbd4yrotj" UNIQUE ("api_key");
ALTER TABLE "public"."sys_api_key" ADD CONSTRAINT "ukl2onfr9vsy0muotxl31cnb0rf" UNIQUE ("api_secret");

-- ----------------------------
-- Checks structure for table sys_api_key
-- ----------------------------
ALTER TABLE "public"."sys_api_key" ADD CONSTRAINT "sys_api_key_status_check" CHECK (status >= 0 AND status <= 1);
ALTER TABLE "public"."sys_api_key" ADD CONSTRAINT "sys_api_key_type_check" CHECK (type >= 0 AND type <= 1);

-- ----------------------------
-- Primary Key structure for table sys_api_key
-- ----------------------------
ALTER TABLE "public"."sys_api_key" ADD CONSTRAINT "sys_api_key_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_apis
-- ----------------------------
ALTER TABLE "public"."sys_apis" ADD CONSTRAINT "sys_apis_pkey" PRIMARY KEY ("operation_id");

-- ----------------------------
-- Indexes structure for table sys_dept
-- ----------------------------
CREATE INDEX "idxg1ucv3qp5oi17u8gs0f2dniqq" ON "public"."sys_dept" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "sys_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_data
-- ----------------------------
CREATE INDEX "idxeywdj5h93uavt3kx34wb7r7b5" ON "public"."sys_dict_data" USING btree (
  "dict_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Checks structure for table sys_dict_data
-- ----------------------------
ALTER TABLE "public"."sys_dict_data" ADD CONSTRAINT "sys_dict_data_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_dict_data
-- ----------------------------
ALTER TABLE "public"."sys_dict_data" ADD CONSTRAINT "sys_dict_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_type
-- ----------------------------
CREATE INDEX "idxjffhq298y61mdc7l0r4oywkem" ON "public"."sys_dict_type" USING btree (
  "dict_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "public"."sys_dict_type" ADD CONSTRAINT "ukjffhq298y61mdc7l0r4oywkem" UNIQUE ("dict_type");

-- ----------------------------
-- Checks structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "public"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "public"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table sys_login_log
-- ----------------------------
ALTER TABLE "public"."sys_login_log" ADD CONSTRAINT "sys_login_log_login_type_check" CHECK (login_type::text = 'PC'::text);

-- ----------------------------
-- Primary Key structure for table sys_login_log
-- ----------------------------
ALTER TABLE "public"."sys_login_log" ADD CONSTRAINT "sys_login_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "ukrltamyrpbiedan88hquyfdh1p" UNIQUE ("route_name");

-- ----------------------------
-- Checks structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_menu_type_check" CHECK (menu_type >= 0 AND menu_type <= 1);
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_operation_log
-- ----------------------------
ALTER TABLE "public"."sys_operation_log" ADD CONSTRAINT "sys_operation_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_post
-- ----------------------------
CREATE INDEX "idxsa4qqci9c70k6s9ue4jr5kr29" ON "public"."sys_post" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Checks structure for table sys_post
-- ----------------------------
ALTER TABLE "public"."sys_post" ADD CONSTRAINT "sys_post_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_post
-- ----------------------------
ALTER TABLE "public"."sys_post" ADD CONSTRAINT "sys_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------
CREATE INDEX "idx4uop4lnlgqfpjukbptwdvpllh" ON "public"."sys_role" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxplpigyqwsqfn7mn66npgf9ftp" ON "public"."sys_role" USING btree (
  "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Checks structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_data_scope_check" CHECK (data_scope >= 0 AND data_scope <= 5);
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role_api
-- ----------------------------
CREATE INDEX "idx4r23x1842ifyenspu70m5bex1" ON "public"."sys_role_api" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "operation_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role_api
-- ----------------------------
ALTER TABLE "public"."sys_role_api" ADD CONSTRAINT "sys_role_api_pkey" PRIMARY KEY ("operation_id", "role_id", "tenant_id");

-- ----------------------------
-- Indexes structure for table sys_role_menu
-- ----------------------------
CREATE INDEX "idxrof491r3v9slwt0pqxrm2m9n6" ON "public"."sys_role_menu" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "menu_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "public"."sys_role_menu" ADD CONSTRAINT "sys_role_menu_pkey" PRIMARY KEY ("menu_id", "role_id", "tenant_id");

-- ----------------------------
-- Indexes structure for table sys_role_user
-- ----------------------------
CREATE INDEX "idxai5euwq3k00rfq12su4u9kngk" ON "public"."sys_role_user" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role_user
-- ----------------------------
ALTER TABLE "public"."sys_role_user" ADD CONSTRAINT "sys_role_user_pkey" PRIMARY KEY ("role_id", "tenant_id", "user_id");

-- ----------------------------
-- Indexes structure for table sys_tenant
-- ----------------------------
CREATE INDEX "idxncbneyk5qqx02wn0py3mb6ief" ON "public"."sys_tenant" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxpa5owowqs8vis7xtxpff3vqx3" ON "public"."sys_tenant" USING btree (
  "contact_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "ukncbneyk5qqx02wn0py3mb6ief" UNIQUE ("name");

-- ----------------------------
-- Checks structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "sys_tenant_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "sys_tenant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE INDEX "idxahtq5ew3v0kt1n7hf1sgp7p8l" ON "public"."sys_user" USING btree (
  "email" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxd01s6x3jgg3ju4mah1kx5rbdq" ON "public"."sys_user" USING btree (
  "account_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxf11inyen634waovwneyctuhoo" ON "public"."sys_user" USING btree (
  "phone_number" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxmeagwplhg664l9dk6ki6c8baf" ON "public"."sys_user" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Checks structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_gender_check" CHECK (gender >= 0 AND gender <= 2);
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_status_check" CHECK (status >= 0 AND status <= 1);

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");
