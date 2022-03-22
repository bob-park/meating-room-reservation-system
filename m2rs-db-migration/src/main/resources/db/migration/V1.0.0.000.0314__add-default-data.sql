/* DML */
-- default company
insert into m2rs_db.company (id, name, logo_path, created_date, last_modified_date)
values (1, 'hwpark-co', '1/5ef0604c-cd4d-472d-accd-4cdc2c5c8683.png', '2022-02-11 15:24:58',
        '2022-02-25 15:42:04');

-- department
insert into m2rs_db.departments (id, com_id, name)
values (1, 1, '혀누 부서');

-- default user
insert into m2rs_db.users (id, department_id, email, password, name, phone, cell_phone)
values (1, null, 'admin@admin.com',
        '{bcrypt}$2a$12$Ng5UfR3Vvk8ku3jZT1HA7.9fNG21yt3WgfdVZUzZdS7PBOmazMjwa', 'admin', null,
        null),
       (2, 1, 'manager@manager.com',
        '{bcrypt}$2a$12$daP6XcPKuSs5WZfijnbSSO6MPTW1PgWqZXjN91PvbFL4XXd8pg37a', 'manager', null,
        null),
       (3, 1, 'user@user.com',
        '{bcrypt}$2a$12$NXoIqhAD8ZYBkK1oOp2i7uXmGIMw6xIDNK8ziX1eDKaad2C5Yf97e', 'user', null, null),
       (4, 1, 'hwpark@hwpark.com',
        '{bcrypt}$2a$10$ccF8WU4asfO7MTF/CpLy0.7NxPUHhcgUNZeXWec7d8YUm47gE.jyW', '혀누 팤', null, null);

-- default roles
insert into roles (id, pid, roles_name, roles_description)
values (1, null, 'ROLE_ADMIN', '시스템 관리자'),
       (2, 1, 'ROLE_MANAGER', '매니저'),
       (3, 2, 'ROLE_USER', '사용자');

-- default users_roles
insert into m2rs_db.users_roles (id, user_id, role_id)
values (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 3);

-- default resources
insert into m2rs_db.resources (id, resource_name, resource_kind, http_method, order_num, resource_type)
values  (1, '/admin/**', 'PATH', null, 1, 'URL'),
        (1011, '^/company/(\\d+)', 'REGEX', null, 1011, 'URL'),
        (1012, '^/company/(\\d+)', 'REGEX', 'GET', 1012, 'URL'),
        (1015, '^/company/(\\d+)/logo', 'REGEX', 'GET', 1015, 'URL'),
        (1101, '^/company/(\\d+)/user', 'REGEX', 'POST', 1101, 'URL'),
        (1105, '^/company/(\\d+)/user(/.*)?', 'REGEX', null, 1105, 'URL'),
        (1110, '^/company/(\\d+)/user/(\\d+)', 'REGEX', null, 1110, 'URL'),
        (1111, '^/company/(\\d+)/user/(\\d+)', 'REGEX', 'DELETE', 1111, 'URL'),
        (1201, '^/company/(\\d+)/department', 'REGEX', 'POST', 1201, 'URL'),
        (1205, '^/company/(\\d+)/department(/.*)?', 'REGEX', null, 1205, 'URL'),
        (1211, '^/company/(\\d+)/department/(\\d+)', 'REGEX', null, 1211, 'URL');

-- default resources_roles
insert into m2rs_db.resources_roles (id, resource_id, role_id)
values  (1, 1, 1),
        (1011, 1011, 1),
        (1101, 1101, 2),
        (1105, 1105, 3),
        (1110, 1110, 3),
        (1111, 1111, 2),
        (1201, 1201, 2),
        (1205, 1205, 2),
        (1211, 1211, 2);