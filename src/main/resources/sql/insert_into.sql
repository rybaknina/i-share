INSERT INTO i_share.course (title, content, level, limit_members, active, donate_type, amount,
                                theme_id, owner_id)
VALUES ('Course for theme 1', 'string', 2, 3, 1, 'FREE', 0.00, 1, 2);
INSERT INTO i_share.course (title, content, level, limit_members, active, donate_type, amount,
                                theme_id, owner_id)
VALUES ('Course for theme 2', 'string', 7, 1, 1, 'FIXED', 100.00, 2, 2);
INSERT INTO i_share.course (title, content, level, limit_members, active, donate_type, amount,
                                theme_id, owner_id)
VALUES ('Course 2 in theme 2', 'string', 3, 10, 1, 'NON_FIXED', 0.00, 2, 2);


INSERT INTO i_share.lesson (title, content, type, level, active, course_id)
VALUES ('lesson 1', 'string', 'THEORY', 5, 1, 1);
INSERT INTO i_share.lesson (title, content, type, level, active, course_id)
VALUES ('lesson 2', 'string', 'THEORY', 6, 1, 1);
INSERT INTO i_share.lesson (title, content, type, level, active, course_id)
VALUES ('lesson 3 upd', NULL, 'THEORY', 0, 1, 1);

INSERT INTO i_share.schedule (start_date, period, lesson_id)
VALUES ('2020-12-07 16:00:00', 45, 2);
INSERT INTO i_share.schedule (start_date, period, lesson_id)
VALUES ('2020-12-07 18:00:00', 45, 3);
INSERT INTO i_share.schedule (start_date, period, lesson_id)
VALUES ('2020-12-08 14:00:00', 45, 1);
INSERT INTO i_share.schedule (start_date, period, lesson_id)
VALUES ('2020-12-09 14:00:00', 45, 1);
INSERT INTO i_share.schedule (start_date, period, lesson_id)
VALUES ('2020-12-10 14:00:00', 45, 1);


INSERT INTO i_share.feedback (text, posted_date, lesson_id, course_id, user_id)
VALUES ('feedback bad', '2020-12-03 10:52:04', 1, NULL, 2);
INSERT INTO i_share.feedback (text, posted_date, lesson_id, course_id, user_id)
VALUES ('feedback good', '2020-12-03 10:52:28', 1, NULL, 2);
INSERT INTO i_share.feedback (text, posted_date, lesson_id, course_id, user_id)
VALUES ('feedback course 1 good', '2020-12-03 10:52:52', NULL, 1, 2);
INSERT INTO i_share.feedback (text, posted_date, lesson_id, course_id, user_id)
VALUES ('feedback course 1 super', '2020-12-03 10:53:00', NULL, 1, 2);

INSERT INTO i_share.donate (creation_date, donation, user_id, course_id)
VALUES ('2020-12-03 11:09:04', 100.00, 2, 2);
INSERT INTO i_share.donate (creation_date, donation, user_id, course_id)
VALUES ('2020-12-03 11:09:20', 110.00, 2, 3);

INSERT INTO i_share.user_role (user_id, role_id)
VALUES (2, 3);
INSERT INTO i_share.user_role (user_id, role_id)
VALUES (2, 4);

INSERT INTO i_share.user_course (user_id, course_id)
VALUES (1, 2);