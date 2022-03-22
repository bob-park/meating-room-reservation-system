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
    roles_description  varchar(50) NOT NULL,
    created_date       datetime    NOT NULL DEFAULT now(),
    last_modified_date datetime
) CHARSET = utf8mb4
  collate = utf8mb4_general_ci;

-- resources
CREATE TABLE resources
(
    id                 bigint       NOT NULL primary key,
    resource_name      varchar(255) NOT NULL,
    resource_kind      varchar(10)  NOT NULL,
    http_method        varchar(10),
    order_num          int(10) not null,
    resource_type      varchar(10)  not null,
    created_date       datetime     NOT NULL DEFAULT now(),
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

-- resources_roles
CREATE TABLE resources_roles
(
    id          bigint NOT NULL primary key,
    resource_id bigint NOT NULL,
    role_id     bigint NOT NULL,

    foreign key (resource_id) references resources (id),
    foreign key (role_id) references roles (id)
) CHARSET = utf8mb4
  collate = utf8mb4_general_ci;