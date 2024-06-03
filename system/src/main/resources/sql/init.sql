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
VALUES ('51', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.function', 'icon-park-outline:all-application', '1', false, 'function', 0, false, 1, '0', null,
        'function', '/function', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('52', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.exception', 'ant-design:exception-outlined', '1', false, 'exception', 0, false, 2, '0', null,
        'exception', '/exception', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('53', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.multi-menu', 'mdi:menu', '1', false, 'multi-menu', 0, false, 3, '0', null, 'multi-menu',
        '/multi-menu', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('54', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.manage', 'carbon:cloud-service-management', '1', false, 'manage', 0, false, 4, '0', null, 'manage',
        '/manage', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('55', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base$view.about',
        false, false, null, 'route.about', 'fluent:book-information-24-regular', '1', false, 'about', 1, false, 5, '0',
        null, 'about', '/about', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('56', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true,
        'layout.base$view.user-center', false, true, null, 'route.user-center', null, '1', false, 'user-center', 0,
        false, 0, '0', null, 'user-center', '/user-center', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('57', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, 'function_tab', true,
        'view.function_multi-tab', false, true, null, 'route.function_multi-tab', 'ic:round-tab', '1', false,
        'function_multi-tab', 1, true, 0, '51', null, 'function_multi-tab', '/function/multi-tab', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('58', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.function_request',
        false, false, null, 'route.function_request', 'carbon:network-overlay', '1', false, 'function_request', 1,
        false, 2, '51', null, 'function_request', '/function/request', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('59', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.function_super-page',
        false, false, null, 'route.function_super-page', 'ic:round-supervisor-account', '1', false,
        'function_super-page', 1, false, 4, '51', null, 'function_super-page', '/function/super-page', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('60', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.function_tab', false,
        false, null, 'route.function_tab', 'ic:round-tab', '1', false, 'function_tab', 1, false, 1, '51', null,
        'function_tab', '/function/tab', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('61', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.function_toggle-auth',
        false, false, null, 'route.function_toggle-auth', 'ic:round-construction', '1', false, 'function_toggle-auth',
        1, false, 3, '51', null, 'function_toggle-auth', '/function/toggle-auth', 0)
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
VALUES ('66', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.403', false, false,
        null, 'route.exception_403', 'ic:baseline-block', '1', false, 'exception_403', 1, false, 0, '52', null,
        'exception_403', '/exception/403', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('67', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.404', false, false,
        null, 'route.exception_404', 'ic:baseline-web-asset-off', '1', false, 'exception_404', 1, false, 1, '52', null,
        'exception_404', '/exception/404', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('68', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'view.500', false, false,
        null, 'route.exception_500', 'ic:baseline-wifi-off', '1', false, 'exception_500', 1, false, 2, '52', null,
        'exception_500', '/exception/500', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('69', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.multi-menu_first', 'mdi:menu', '1', false, 'multi-menu_first', 0, false, 0, '53', null,
        'multi-menu_first', '/multi-menu/first', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('70', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.multi-menu_second', 'mdi:menu', '1', false, 'multi-menu_second', 0, false, 1, '53', null,
        'multi-menu_second', '/multi-menu/second', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('71', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true,
        'view.multi-menu_first_child', false, false, null, 'route.multi-menu_first_child', 'mdi:menu', '1', false,
        'multi-menu_first_chi', 1, false, 0, '69', null, 'multi-menu_first_child', '/multi-menu/first/child', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('72', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true, 'layout.base', false, false,
        null, 'route.multi-menu_second_child', 'mdi:menu', '1', false, 'multi-menu_second_ch', 0, false, 0, '70', null,
        'multi-menu_second_child', '/multi-menu/second/child', 0)
ON CONFLICT (id) DO NOTHING;
INSERT INTO sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, active_menu, built_in, component, constant, hide_in_menu, href, i18n_key,
                      icon,
                      icon_type, keep_alive, menu_name, menu_type, multi_tab, sequence, parent_id, roles,
                      route_name, route_path, status)
VALUES ('73', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, null, true,
        'view.multi-menu_second_child_home', false, false, null, 'route.multi-menu_second_child_home', 'mdi:menu', '1',
        false, 'multi-menu_second_ch', 1, false, 0, '72', null, 'multi-menu_second_child_home',
        '/multi-menu/second/child/home', 0)
ON CONFLICT (id) DO NOTHING;

-- sys_role
INSERT INTO sys_role (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, tenant_id, built_in, code, data_scope, data_scope_dept_ids, name, sequence,
                      remark, status)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', true, 'ROLE_SYSTEM_ADMIN', 0, null,
        '系统管理员', 1, 'Built-in system administrator', 0)
ON CONFLICT (id) DO NOTHING;

-- sys_tenant
INSERT INTO sys_tenant (id, create_account_name, create_by, create_time, update_account_name, update_by,
                        update_time, built_in, contact_account_name, contact_user_id, expire_time, name,
                        status, website)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, true, 'Soybean', 1,
        '2099-12-31 00:00:00.000000', 'Soybean', 0, 'https://soybean.pro')
ON CONFLICT (id) DO NOTHING;

-- sys_role_user
INSERT INTO sys_role_user (id, create_account_name, create_by, create_time, update_account_name, update_by,
                           update_time, tenant_id, role_id, user_id)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', '1', '1')
ON CONFLICT (id) DO NOTHING;

-- sys_user
INSERT INTO sys_user (id, create_account_name, create_by, create_time, update_account_name, update_by,
                      update_time, tenant_id, account_name, account_password, avatar, built_in, country_code,
                      dept_id, email, gender, nick_name, personal_profile, phone_code, phone_number, status)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', 'Soybean',
        '$2a$10$BrNwelZswsGy9FGCTARd5efBtM0Ra4Xz8e8DoT86EOju9Ii0jpBg6',
        'https://minio.bytebytebrew.com/default/Ugly%20Avatar%20Face.png', true, 'CN', null, null, 0, 'Soybean',
        'Built-in super administrator user', '86', null, 0)
ON CONFLICT (id) DO NOTHING;

-- sys_api_key
INSERT INTO sys_api_key (id, create_account_name, create_by, create_time, update_account_name, update_by,
                         update_time, tenant_id, api_key, status, type, used_count, api_secret)
VALUES ('1', 'built-in', '-1', '2024-01-01 00:00:00.000000', null, null, null, '1', 'Soybean_key', 0, 0, 0,
        'Soybean_secret')
ON CONFLICT (id) DO NOTHING;
