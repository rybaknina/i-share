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

