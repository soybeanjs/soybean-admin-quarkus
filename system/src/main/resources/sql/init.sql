-- sys_menu
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.blank$view.login',
        true, true, null, 'route.login', null, '1', false, 'login', 1, false, 0, '0', null, 'login',
        '/login/:module(pwd-login|code-login|register|reset-pwd|bind-wechat)?', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('2', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.blank$view.403',
        true, true, null, 'route.403', null, '1', false, '403', 1, false, 0, '0', null, '403', '/403', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('3', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.blank$view.404',
        true, true, null, 'route.404', null, '1', false, '404', 1, false, 0, '0', null, '404', '/404', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('4', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.blank$view.500',
        true, true, null, 'route.500', null, '1', false, '500', 1, false, 0, '0', null, '500', '/500', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('5', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true,
        'layout.base$view.iframe-page', true, true, null, 'route.iframe-page', null, '1', false, 'iframe-page', 1,
        false, 0, '0', null, 'iframe-page', '/iframe-page/:url', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('50', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base$view.home',
        false, false, null, 'route.home', 'mdi:monitor-dashboard', '1', false, 'home', 1, false, 0, '0', null, 'home',
        '/home', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('54', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.manage', 'carbon:cloud-service-management', '1', false, 'manage', 0, false, 1, '0', null, 'manage',
        '/manage', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('62', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.manage_menu', false,
        false, null, 'route.manage_menu', 'material-symbols:route', '1', true, 'manage_menu', 1, false, 2, '54', null,
        'manage_menu', '/manage/menu', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('63', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.manage_role', false,
        false, null, 'route.manage_role', 'carbon:user-role', '1', false, 'manage_role', 1, false, 1, '54', null,
        'manage_role', '/manage/role', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('64', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.manage_user', false,
        false, null, 'route.manage_user', 'ic:round-manage-accounts', '1', false, 'manage_user', 1, false, 0, '54',
        null, 'manage_user', '/manage/user', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('65', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, 'manage_user', true,
        'view.manage_user-detail', false, true, null, 'route.manage_user-detail', null, '1', false,
        'manage_user-detail', 1, false, 3, '54', null, 'manage_user-detail', '/manage/user-detail/:id', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('66', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.manage_tenant', false,
        false, null, 'route.manage_tenant', 'arcticons:tenantcloud-pro', '1', false, 'manage_tenant', 1, false, 1, '54', null,
        'manage_tenant', '/manage/tenant', 0)
ON CONFLICT (id) DO NOTHING;

-- sys_role
INSERT INTO sys_role (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, tenant_id, built_in, code, data_scope, data_scope_dept_ids, name, sequence,
                      remark, status)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', true, 'ROLE_SYSTEM_ADMIN', 0, null,
        '系统管理员', 1, 'Built-in system administrator', 0)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_role (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, tenant_id, built_in, code, data_scope, data_scope_dept_ids, name, sequence,
                      remark, status)
VALUES ('2', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '2', false, 'ROLE_TENANT_ADMIN', 0, null,
        '租户管理员', 2, 'Tenant administrator for testing purposes', 0)
ON CONFLICT (id) DO NOTHING;

-- sys_tenant
INSERT INTO sys_tenant (id, create_account_name, create_by, create_time, update_account_name, update_by,
                        update_time, built_in, contact_account_name, contact_user_id, expire_time, name,
                        status, website)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, true, 'Soybean', 1,
        '2099-12-31 00:00:00.000000', 'Soybean', 0, 'https://soybean.pro')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_tenant (id, create_account_name, create_by, create_time, update_account_name, update_by,
                        update_time, built_in, contact_account_name, contact_user_id, expire_time, name,
                        status, website, menu_ids, operation_ids)
VALUES ('2', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, false, 'TestAdmin', 2,
        '2099-12-31 00:00:00.000000', 'TestTenant', 0, 'https://testtenant.example.com',
        '["50","54","62","63","64","65"]',
        '["OP_4F801E1A0187F504B6A1CB9B064C0204", "OP_6ED2382E5D610355A4C02563E6A41372", "OP_D1C9F7CD5DB776A4DB0EE42022ABB86F", "OP_4AA5B4A1792D02F31FEEC5C3932E8659", "OP_27BB69ABA98EAEB2F3B7FEAD48A93FA9", "OP_12BF02E8FE64E792A2B397D3F62E3AFF", "OP_6FA02D7F595DA146E68CA76F4EF094BD", "OP_ED729932EF80ECC74A5A281C913D03B6", "OP_B6B43F46EC2C1CCFCB170DE3A1CF0D51", "OP_4706DE447DA86F81D8B1DFA41507A445", "OP_46587805611E27AA7AE2A4BC5ADB5351", "OP_F46E7A428EBB04D2A3F18336C3759A1A", "OP_8B9F9107AB120F291C983F5AAC47BFFA", "OP_1C70A8D93030ADFA5740C925B3C2135D", "OP_1EFD09536C62824CDEE621F78E5FBEC3", "OP_3A1707247B9450C97DE65B0586C9F4AA"]')
ON CONFLICT (id) DO NOTHING;

-- sys_role_user
INSERT INTO sys_role_user (role_id, user_id, tenant_id)
VALUES ('1', '1', '1')
ON CONFLICT (role_id, user_id, tenant_id) DO NOTHING;

INSERT INTO sys_role_user (role_id, user_id, tenant_id)
VALUES ('2', '2', '2')
ON CONFLICT (role_id, user_id, tenant_id) DO NOTHING;

-- sys_user
INSERT INTO sys_user (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, tenant_id, account_name, account_password, avatar, built_in, country_code,
                      dept_id, email, gender, nick_name, personal_profile, phone_code, phone_number, status)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', 'Soybean',
        '$2a$10$BrNwelZswsGy9FGCTARd5efBtM0Ra4Xz8e8DoT86EOju9Ii0jpBg6',
        'https://minio.bytebytebrew.com/default/Ugly%20Avatar%20Face.png', true, 'CN', null, null, 0, 'Soybean',
        'Built-in super administrator user', '86', null, 0)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_user (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, tenant_id, account_name, account_password, avatar, built_in, country_code,
                      dept_id, email, gender, nick_name, personal_profile, phone_code, phone_number, status)
VALUES ('2', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '2', 'TestAdmin',
        '$2a$10$BrNwelZswsGy9FGCTARd5efBtM0Ra4Xz8e8DoT86EOju9Ii0jpBg6',
        'https://minio.bytebytebrew.com/default/Ugly%20Avatar%20Face.png', false, 'CN', null, 'admin@test.com', 0, 'Test Admin',
        'Admin user for testing purposes', '86', null, 0)
ON CONFLICT (id) DO NOTHING;

-- sys_api_key
INSERT INTO sys_api_key (id, create_account_name, create_by, create_time, update_account_name, update_by,
                         update_time, tenant_id, api_key, status, type, used_count, api_secret)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', 'Soybean_key', 0, 0, 0,
        'Soybean_secret')
ON CONFLICT (id) DO NOTHING;

-- sys_role_menu
INSERT INTO sys_role_menu (role_id, menu_id, tenant_id)
VALUES
('2', '50', '2'),
('2', '54', '2'),
('2', '62', '2'),
('2', '63', '2'),
('2', '64', '2'),
('2', '65', '2')
ON CONFLICT (role_id, menu_id, tenant_id) DO NOTHING;

-- sys_role_api
INSERT INTO sys_role_api (role_id, operation_id, tenant_id)
VALUES
('2', 'OP_4F801E1A0187F504B6A1CB9B064C0204', '2'),
('2', 'OP_6ED2382E5D610355A4C02563E6A41372', '2'),
('2', 'OP_D1C9F7CD5DB776A4DB0EE42022ABB86F', '2'),
('2', 'OP_4AA5B4A1792D02F31FEEC5C3932E8659', '2'),
('2', 'OP_27BB69ABA98EAEB2F3B7FEAD48A93FA9', '2'),
('2', 'OP_12BF02E8FE64E792A2B397D3F62E3AFF', '2'),
('2', 'OP_6FA02D7F595DA146E68CA76F4EF094BD', '2'),
('2', 'OP_ED729932EF80ECC74A5A281C913D03B6', '2'),
('2', 'OP_B6B43F46EC2C1CCFCB170DE3A1CF0D51', '2'),
('2', 'OP_4706DE447DA86F81D8B1DFA41507A445', '2'),
('2', 'OP_46587805611E27AA7AE2A4BC5ADB5351', '2'),
('2', 'OP_F46E7A428EBB04D2A3F18336C3759A1A', '2'),
('2', 'OP_8B9F9107AB120F291C983F5AAC47BFFA', '2'),
('2', 'OP_1C70A8D93030ADFA5740C925B3C2135D', '2'),
('2', 'OP_1EFD09536C62824CDEE621F78E5FBEC3', '2'),
('2', 'OP_3A1707247B9450C97DE65B0586C9F4AA', '2')
ON CONFLICT (role_id, operation_id, tenant_id) DO NOTHING;
