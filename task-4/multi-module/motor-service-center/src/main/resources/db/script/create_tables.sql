-- -----------------------------------------------------
-- Schema motor_service_service
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `motor_service_service` ;

-- -----------------------------------------------------
-- Schema motor_service_service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `motor_service_service` DEFAULT CHARACTER SET utf8 ;
USE `motor_service_service` ;

-- -----------------------------------------------------
-- Table `motor_service_service`.`garage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`garage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`mechanic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`mechanic` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `garage_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_mechanic_garage_idx` (`garage_id` ASC),
  CONSTRAINT `fk_mechanic_garage`
    FOREIGN KEY (`garage_id`)
    REFERENCES `motor_service_service`.`garage` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`spot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`spot` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `garage_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_spot_garage_idx` (`garage_id` ASC),
  CONSTRAINT `fk_spot_garage`
    FOREIGN KEY (`garage_id`)
    REFERENCES `motor_service_service`.`garage` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `request_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `planned_date` TIMESTAMP NULL,
  `start_date` TIMESTAMP NULL,
  `complete_date` TIMESTAMP NULL,
  `price` DECIMAL(17,2) NULL,
  `status` VARCHAR(15) NULL,
  `mechanic_id` INT NULL,
  `spot_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_mechanic_idx` (`mechanic_id` ASC),
  INDEX `fk_order_spot_idx` (`spot_id` ASC),
  CONSTRAINT `fk_order_mechanic`
    FOREIGN KEY (`mechanic_id`)
    REFERENCES `motor_service_service`.`mechanic` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_spot`
    FOREIGN KEY (`spot_id`)
    REFERENCES `motor_service_service`.`spot` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`tool`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`tool` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `hours` TINYINT(4) NULL,
  `hourly_price` DECIMAL(17,2) NULL,
  `order_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tool_order_idx` (`order_id` ASC),
  CONSTRAINT `fk_tool_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `motor_service_service`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `motor_service_service`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `active` TINYINT NOT NULL DEFAULT 1,
  `account_non_expired` TINYINT NOT NULL DEFAULT 1,
  `account_non_locked` TINYINT NOT NULL DEFAULT 1,
  `credentials_non_expired` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`role_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`role_permission` (
   `role_id` INT NOT NULL,
   `permission_id` INT NOT NULL,
   PRIMARY KEY (`role_id`, `permission_id`),
  INDEX `fk_role_has_permission_permission1_idx` (`permission_id` ASC),
  INDEX `fk_role_has_permission_role1_idx` (`role_id` ASC),
  CONSTRAINT `fk_role_has_permission_role1`
  FOREIGN KEY (`role_id`)
  REFERENCES `motor_service_service`.`role` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  CONSTRAINT `fk_role_has_permission_permission1`
  FOREIGN KEY (`permission_id`)
  REFERENCES `motor_service_service`.`permission` (`id`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `motor_service_service`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `motor_service_service`.`user_role` (
   `user_id` INT NOT NULL,
   `role_id` INT NOT NULL,
   PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_has_role_role1_idx` (`role_id` ASC),
  INDEX `fk_user_has_role_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_role_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `motor_service_service`.`user` (`id`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_role_role1`
  FOREIGN KEY (`role_id`)
  REFERENCES `motor_service_service`.`role` (`id`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE)
  ENGINE = InnoDB;