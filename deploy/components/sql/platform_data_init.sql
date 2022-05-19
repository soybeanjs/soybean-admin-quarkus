insert into sys_oauth_client_details (client_id, client_secret, status, type, resource_ids, client_name, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
values  ('client', 'client', true, 0, null, '客户端', 'server', 'password,client_credentials,authorization_code', null, null, 86400, 604800, null, null);

insert into sys_role (id, tenant_id, code, name, description, scope_type, locked, super, readonly, created_by, created_name, created_time, last_modified_by, last_modified_name, last_modified_time)
values  (1, 1, 'PLATFORM_ADMIN', '平台管理员', '平台管理员', 50, 0, 1, 1, null, null, null, null, null, null);

insert into sys_user_role (user_id, role_id)
values  (1, 1);

insert into t_dynamic_datasource (id, pool_name, username, password, db_type, driver_class_name, database, connection_type, host, port, description, locked, created_by, created_name, created_time, last_modified_by, last_modified_name, last_modified_time)
values  (1, 'TenantDataSourcePool', 'root', '123456', 'mysql', 'com.mysql.cj.jdbc.Driver', 'soybean', 0, '127.0.0.1:3306', 3306, 'tenant-8888', false, null, null, null, null, null, null);

insert into t_tenant_config (id, tenant_id, dynamic_datasource_id, created_by, created_name, created_time, last_modified_by, last_modified_name, last_modified_time)
values  (1, 1, 1, null, null, null, null, null, null);

insert into t_tenant (id, code, name, type, status, alias, logo, email, contact_person, contact_phone, industry, province_id, province_name, city_id, city_name, address, district_id, district_name, credit_code, legal_person_name, web_site, description, locked, created_by, created_name, created_time, last_modified_by, last_modified_name, last_modified_time)
values  (1, '0000', '超级租户', 1, 1, '超级租户', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, null, null, null);

insert into t_user (id, tenant_id, username, password, nick_name, org_id, station_id, readonly, email, mobile, id_card, sex, status, avatar, description, nation, education, position_status, birthday, created_by, created_name, created_time, last_modified_by, last_modified_name, last_modified_time)
values  (1, null, 'admin', '{bcrypt}$2a$10$R2AdNVf402GnqcJejdjY..wOHP5hFt5x0vz5qXdTVG.udcdFmqu.K', 'admin', null, null, true, null, null, null, null, true, null, null, null, null, null, null, null, null, null, null, '', null);