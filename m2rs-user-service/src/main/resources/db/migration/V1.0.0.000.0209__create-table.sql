/* DDL */
-- company
create table company
(
    id                 bigint      not null primary key,
    name               varchar(20) not null,
    logo_path          text,
    created_date       datetime    not null default now(),
    last_modified_date datetime
) CHARSET = utf8mb4
    collate = utf8mb4_general_ci;

-- departments
create table departments
(
    id                 bigint      not null primary key,
    com_id             bigint      not null,
    name               varchar(20) not null,
    created_date       datetime    not null default now(),
    last_modified_date datetime,

    foreign key (com_id) references company (id)
) CHARSET = utf8mb4
  collate = utf8mb4_general_ci;

-- users
create table users
(
    id                 bigint       not null primary key,
    department_id      bigint,
    email              varchar(50)  not null,
    password           varchar(255) not null,
    name               varchar(20)  not null,
    phone              varchar(20),
    cell_phone         varchar(2),
    created_date       datetime     not null default now(),
    last_modified_date datetime,

    foreign key (department_id) references departments (id)
) CHARSET = utf8mb4
  collate = utf8mb4_general_ci;

-- roles
CREATE TABLE roles
(
    id                 bigint      NOT NULL primary key,
    pid                bigint,
    roles_name         varchar(20) NOT NULL,
    roles_description  varchar(50) not null,
    created_date       datetime    NOT NULL DEFAULT now(),
    last_modified_date datetime
) CHARSET = utf8mb4
  collate = utf8mb4_general_ci;

-- users_roles
CREATE TABLE users_roles
(
    id      bigint NOT NULL primary key,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,

    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
) CHARSET = utf8mb4
  collate = utf8mb4_general_ci;


/* DML */
-- default user
insert into users (id, department_id, email, password, name)
values (1, null, 'admin@admin.com',
        '{bcrypt}$2a$12$Ng5UfR3Vvk8ku3jZT1HA7.9fNG21yt3WgfdVZUzZdS7PBOmazMjwa', 'admin');

-- default roles
insert into roles (id, pid, roles_name, roles_description)
values (1, null, 'ROLE_ADMIN', '시스템 관리자'),
       (2, 1, 'ROLE_MANAGER', '매니저'),
       (3, 2, 'ROLE_USER', '사용자');

-- default user roles
insert into m2rs_db.users_roles (id, user_id, role_id)
values (1, 1, 1);

