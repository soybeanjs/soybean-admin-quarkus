-- sys_menu
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (1, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'layout.base$view.home', false,
        'route.home', 'mdi:monitor-dashboard', '首页', 1, null, 10, 0, 'home', '/home', 0, null, null, null, null, '1')
ON CONFLICT (id) DO NOTHING;
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (2, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'layout.base', false, 'route.manage',
        'carbon:cloud-service-management', '系统管理', 0, null, 20, 0, 'manage', '/manage', 0, null, null, null, null,
        '1')
ON CONFLICT (id) DO NOTHING;
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (3, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'view.manage_user', false,
        'route.manage_user', 'ic:round-manage-accounts', '用户管理', 1, null, 1, 2, 'manage_user', '/manage/user', 0,
        null, null, null, null, '1')
ON CONFLICT (id) DO NOTHING;
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (4, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'view.manage_role', false,
        'route.manage_role', 'carbon:user-role', '角色管理', 1, null, 2, 2, 'manage_role', '/manage/role', 0, null,
        null, null, null, '1')
ON CONFLICT (id) DO NOTHING;
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (5, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'view.manage_menu', false,
        'route.manage_menu', 'material-symbols:route', '菜单管理', 1, null, 3, 2, 'manage_menu', '/manage/menu', 0,
        null, null, null, null, '1')
ON CONFLICT (id) DO NOTHING;
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (6, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'view.manage_user-detail',
        true, 'route.manage_user-detail', null, '用户详情', 1, null, 4, 2, 'manage_user-detail',
        '/manage/user-detail/:id', 0, null, null, null, null, '1')
ON CONFLICT (id) DO NOTHING;
INSERT INTO public.sys_menu (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, active_menu, component, hide_in_menu, i18n_key, icon, menu_name, menu_type,
                             multi_tab, sequence, parent_id, route_name, route_path, status, constant, href, keep_alive,
                             roles, icon_type)
VALUES (7, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, null, 'layout.base$view.about', false,
        'route.about', 'fluent:book-information-24-regular', '关于', 1, null, 30, 0, 'about', '/about', 0, null, null,
        null, null, '1')
ON CONFLICT (id) DO NOTHING;

-- sys_role
INSERT INTO public.sys_role (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, tenant_id, builtin, code, data_scope, data_scope_dept_ids, name, sequence,
                             remark, status)
VALUES (1, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, 1, true, 'ROLE_SYSTEM_ADMIN', 0, null,
        '系统管理员', 1, 'Built-in system administrator', 0)
ON CONFLICT (id) DO NOTHING;

-- sys_tenant
INSERT INTO public.sys_tenant (id, create_account_name, create_by, create_time, update_account_name, update_by,
                               update_time, builtin, contact_account_name, contact_user_id, expire_time, name,
                               package_id, status, website)
VALUES (1, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, true, 'Soybean', 1,
        '2099-12-31 00:00:00.000000', 'Soybean', 0, 0, 'https://soybean.pro')
ON CONFLICT (id) DO NOTHING;

-- sys_user_role
INSERT INTO public.sys_user_role (id, create_account_name, create_by, create_time, update_account_name, update_by,
                                  update_time, tenant_id, role_id, user_id)
VALUES (1, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, 1, 1, 1)
ON CONFLICT (id) DO NOTHING;

-- sys_user
INSERT INTO public.sys_user (id, create_account_name, create_by, create_time, update_account_name, update_by,
                             update_time, tenant_id, account_name, account_password, avatar, builtin, country_code,
                             dept_id, email, gender, nick_name, personal_profile, phone_code, phone_number, status)
VALUES (1, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, 1, 'Soybean',
        '$2a$10$BrNwelZswsGy9FGCTARd5efBtM0Ra4Xz8e8DoT86EOju9Ii0jpBg6', null, true, 'CN', null, null, 0, 'Soybean',
        'Built-in super administrator user', '86', null, 0)
ON CONFLICT (id) DO NOTHING;

-- sys_api_key
INSERT INTO public.sys_api_key (id, create_account_name, create_by, create_time, update_account_name, update_by,
                                update_time, tenant_id, api_key, status, type, used_count, api_secret)
VALUES (1, 'builtin', -1, '2024-01-01 00:00:00.000000', null, null, null, 1, 'Soybean_key', 0, 0, 0, 'Soybean_secret')
ON CONFLICT (id) DO NOTHING;
