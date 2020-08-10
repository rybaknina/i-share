-- -----------------------------------------------------
-- Schema task10db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `task10db` ;

-- -----------------------------------------------------
-- Schema task10db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `task10db` DEFAULT CHARACTER SET utf8 ;
USE `task10db` ;

-- -----------------------------------------------------
-- Table `task10db`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `task10db`.`product` (
  `maker` VARCHAR(10) NOT NULL,
  `model` VARCHAR(50) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`model`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `task10db`.`pc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `task10db`.`pc` (
  `code` INT NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `speed` SMALLINT(5) NOT NULL,
  `ram` SMALLINT(5) NOT NULL,
  `hd` REAL NOT NULL,
  `cd` VARCHAR(10) NOT NULL,
  `price` DECIMAL(15,0) NULL,
  PRIMARY KEY (`code`),
  INDEX `model_idx` (`model` ASC) INVISIBLE,
  CONSTRAINT `pc_fk`
    FOREIGN KEY (`model`)
    REFERENCES `task10db`.`product` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `task10db`.`laptop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `task10db`.`laptop` (
  `code` INT NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `speed` SMALLINT(5) NOT NULL,
  `ram` SMALLINT(5) NOT NULL,
  `hd` REAL NOT NULL,
  `price` DECIMAL(15,0) NULL,
  `screen` TINYINT(3) NOT NULL,
  PRIMARY KEY (`code`),
  INDEX `model_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `laptop_fk`
    FOREIGN KEY (`model`)
    REFERENCES `task10db`.`product` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `task10db`.`printer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `task10db`.`printer` (
  `code` INT NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `color` VARCHAR(1) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `price` DECIMAL(15,0) NULL,
  PRIMARY KEY (`code`),
  INDEX `model_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `printer_fk`
    FOREIGN KEY (`model`)
    REFERENCES `task10db`.`product` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

