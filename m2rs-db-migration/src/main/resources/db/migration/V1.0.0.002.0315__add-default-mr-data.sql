/* default data */

-- meeting_rooms
INSERT INTO m2rs_db.meeting_rooms (id, com_id, name)
VALUES (1, 1, '혀누 혀누 회의실');

-- meeting_rooms_reservation
INSERT INTO m2rs_db.meeting_room_reservations (id, user_id, mr_id, title,start_date, end_date)
VALUES (1, 4, 1, '이것은 테스트다', '2022-03-15 16:00:00', '2022-03-15 17:00:00');
