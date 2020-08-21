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

