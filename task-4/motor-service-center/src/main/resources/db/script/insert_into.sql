INSERT INTO `motor_service_service`.`garage` (`name`) VALUES
('Garage Slonim'), ('Garage Gomel'), ('Garage Minsk 1'), ('Garage Minsk 2');

INSERT INTO `motor_service_service`.`spot` (`garage_id`) VALUES
(1), (1), (2), (2), (2), (3), (3), (3), (4);

INSERT INTO `motor_service_service`.`mechanic` (`name`, `garage_id`) VALUES
('Ivan Petrov', 1), ('Avto Mechanic', 2), ('Crazy Man', 4);

INSERT INTO `motor_service_service`.`order` (`request_date`, `planned_date`, `start_date`, `complete_date`, `price`, `status`, `mechanic_id`, `spot_id`) VALUES
(CURRENT_TIMESTAMP, '2020-08-15 13:30:00', '2020-08-15 13:30:00', null, null, 'IN_PROGRESS', 1, 1);

INSERT INTO `motor_service_service`.`tool` (`name`, `hours`, `hourly_price`, `order_id`) VALUES
('Check Engine', 2, 3.5, 1), ('Diagnosis', 1, 1, 1), ('Oil Change', 2, 2.5, 1), ('Tyre Change', 1, 1.5, 1);


