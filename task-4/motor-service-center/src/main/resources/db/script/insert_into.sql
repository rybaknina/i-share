INSERT INTO `motor_service_service`.`garage` (`name`) VALUES
('Garage Slonim'), ('Garage Gomel'), ('Garage Minsk 1'), ('Garage Minsk 2');

INSERT INTO `motor_service_service`.`spot` (`garage_id`) VALUES
(1), (1), (2), (2), (3), (4), (4), (4);

INSERT INTO `motor_service_service`.`mechanic` (`name`, `garage_id`) VALUES
('Hulk', 1), ('Spider-Man', 2), ('Iron Man', 3),  ('Thor', 4),
('Captain America', 1), ('Ant-Man', 2), ('Wasp', 3),  ('Black Widow', 4),
('Wolverine', 1), ('Captain Marvel', 2), ('Black Panther', 3),  ('Doctor Strange', 4),
('Ghost Rider', 1), ('Blade', 2), ('Daredevil', 3),  ('Deadpool', 4);

INSERT INTO `motor_service_service`.`order` (`request_date`, `planned_date`, `start_date`, `complete_date`, `price`, `status`, `mechanic_id`, `spot_id`) VALUES
('2020-08-15 13:30:00', '2020-08-15 13:30:00', '2020-08-15 13:30:00', null, null, 'IN_PROGRESS', 12, 1),
('2020-08-16 13:30:00', '2020-08-16 13:30:00', '2020-08-16 13:30:00', null, null, 'IN_PROGRESS', 15, 5),
('2020-08-17 13:30:00', '2020-08-17 13:30:00', '2020-08-17 13:30:00', null, null, 'IN_PROGRESS', 5, 2),
('2020-08-18 13:30:00', '2020-08-18 13:30:00', '2020-08-18 13:30:00', null, null, 'IN_PROGRESS', 4, 4),
('2020-08-19 13:30:00', '2020-08-19 13:30:00', '2020-08-19 13:30:00', null, null, 'IN_PROGRESS', 1, 8),
('2020-08-20 13:30:00', '2020-08-20 13:30:00', '2020-08-20 13:30:00', null, null, 'IN_PROGRESS', 6, 2),
('2020-08-21 13:30:00', '2020-08-21 13:30:00', '2020-08-21 13:30:00', null, null, 'IN_PROGRESS', 3, 3);

INSERT INTO `motor_service_service`.`tool` (`name`, `hours`, `hourly_price`, `order_id`) VALUES
('Check Engine', 2, 3.5, 1), ('Diagnosis', 1, 1, 1),
('Oil Change', 2, 2.5, 2), ('Tyre Change', 1, 1.5, 2), ('Check Engine', 2, 3.5, 2),
('Check Engine', 2, 3.5, 3), ('Diagnosis', 1, 1, 3), ('Oil Change', 2, 2.5, 3);

INSERT INTO `motor_service_service`.`role` (`name`)
VALUES ('ADMIN'), ('USER');

INSERT INTO `motor_service_service`.`permission` (`name`)
VALUES ('read'), ('write'), ('create'), ('delete');

INSERT INTO `motor_service_service`.`user`
(`username`, `password`)
VALUES
('user1', '$2a$04$PfGoAAICZ.firM1gb8H2ruy4nSGxr0QLTG91xVxTy7wXged4j6z1K'),
('user2', '$2a$04$VUovHleEuRv7z58gtCBsKuR3hHvbVnqNgD9AzWl3rNlXdjxyDwI8O'),
('user3', '$2a$04$OcGXTeykEeQtpDX.oRaXuuXJdRzgDSBBrr1C2SKKUFIar.lwMKEOa'),
('user4', '$2a$10$mQq8P9ZpX3wLW0VuR58jU.fdQO5hy6usgmeG7amU91d6n/QCkpcGu'),
('user5', '$2a$10$9wJfmWynHb/LiMV1MoAlxucfgTkMQE5fC4r1LCjtZKWvDUlhCpwNS');

INSERT INTO `motor_service_service`.`user_role`
(`user_id`, `role_id`)
VALUES (1, 1), (2, 2), (3, 2), (1, 4), (3, 2) , (3, 3), (4, 2), (5, 3);

INSERT INTO `motor_service_service`.`role_permission`
(`role_id`, `permission_id`)
VALUES (1, 2), (2, 1), (1, 4), (3, 2) , (3, 3);