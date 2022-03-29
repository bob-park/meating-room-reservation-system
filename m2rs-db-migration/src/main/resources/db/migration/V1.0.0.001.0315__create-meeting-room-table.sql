-- meeting_rooms
create table meeting_rooms
(
    id                 bigint       not null primary key,
    com_id             bigint       not null,
    name               varchar(255) not null,
    is_active          boolean      not null default true,
    created_date       datetime     not null default now(),
    last_modified_date datetime,

    foreign key (com_id) references company (id)
) charset = utf8mb4
  collate = utf8mb4_general_ci;

-- meeting_room_reservations
create table meeting_room_reservations
(
    id                 bigint       not null primary key,
    user_id            bigint       not null,
    mr_id              bigint       not null,
    title              varchar(255) not null,
    description        varchar(2000),
    number_of_users    int          not null default 2,
    start_date         datetime     not null,
    end_date           datetime     not null,
    created_date       datetime     not null default now(),
    last_modified_date datetime,

    foreign key (user_id) references users (id),
    foreign key (mr_id) references meeting_rooms (id)
) charset = utf8mb4
  collate = utf8mb4_general_ci;