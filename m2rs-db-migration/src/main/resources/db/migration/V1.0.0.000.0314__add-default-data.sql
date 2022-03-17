/* DML */
-- default company
insert into m2rs_db.company (id, name, logo_path, created_date, last_modified_date)
values (1, 'hwpark-co', '1/5ef0604c-cd4d-472d-accd-4cdc2c5c8683.png', '2022-02-11 15:24:58',
        '2022-02-25 15:42:04');

-- default user
insert into m2rs_db.users (id, department_id, email, password, name, phone, cell_phone,
                           created_date, last_modified_date)
values (1, null, 'admin@admin.com',
        '{bcrypt}$2a$12$Ng5UfR3Vvk8ku3jZT1HA7.9fNG21yt3WgfdVZUzZdS7PBOmazMjwa', 'admin', null, null,
        '2022-02-11 06:22:35', null),
       (2, null, 'manager@manager.com',
        '{bcrypt}$2a$12$daP6XcPKuSs5WZfijnbSSO6MPTW1PgWqZXjN91PvbFL4XXd8pg37a', 'manager', null,
        null, '2022-03-14 01:13:23', null),
       (3, null, 'user@user.com',
        '{bcrypt}$2a$12$NXoIqhAD8ZYBkK1oOp2i7uXmGIMw6xIDNK8ziX1eDKaad2C5Yf97e', 'user', null, null,
        '2022-03-14 00:55:10', null);


-- default roles
insert into roles (id, pid, roles_name, roles_description)
values (1, null, 'ROLE_ADMIN', '시스템 관리자'),
       (2, 1, 'ROLE_MANAGER', '매니저'),
       (3, 2, 'ROLE_USER', '사용자');

-- default user roles
insert into m2rs_db.users_roles (id, user_id, role_id)
values (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3);

-- default resource
insert into m2rs_db.resources (id, resource_name, http_method, order_num, resource_type)
values (1, '/user/check', 'GET', 1, 'URL'),
       (2, '/user', 'POST', 2, 'URL'),
       (3, '/department', 'POST', 3, 'URL');

insert into m2rs_db.resources_roles (id, resource_id, role_id)
values (1, 1, 3),
       (2, 2, 2),
       (3, 3, 2);