/* default data */
-- department
insert into m2rs_db.departments (id, com_id, name)
values (1001, 1, '혀누 부서');

-- user
insert into m2rs_db.users (id, department_id, email, password, name)
values (1002, 1001, 'hwpark@hwpark.com',
        '{bcrypt}$2a$10$ccF8WU4asfO7MTF/CpLy0.7NxPUHhcgUNZeXWec7d8YUm47gE.jyW', '혀누 팤');

-- user_roles
insert into m2rs_db.users_roles (id, user_id, role_id)
values (1003, 1002, 3);

-- meeting_rooms
INSERT INTO m2rs_db.meeting_rooms (id, com_id, name)
VALUES (1, 1, '혀누 혀누 회의실');

-- meeting_rooms_reservation
INSERT INTO m2rs_db.meeting_room_reservations (id, user_id, mr_id, start_date, end_date)
VALUES (1, 1002, 1, '2022-03-15 16:00:00', '2022-03-15 17:00:00');
