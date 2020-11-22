-- -- -----------------------------------------------------
-- -- Schema i_share
-- -- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS `i_share` ;
--
-- -- -----------------------------------------------------
-- -- Schema i_share
-- -- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS `i_share` DEFAULT CHARACTER SET utf8 ;
-- USE `i_share` ;

-- -----------------------------------------------------
-- Table `i_share`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NULL,
  `birthday` DATE NULL,
  `enable` TINYINT NOT NULL DEFAULT 1,
  `account_non_expired` TINYINT NOT NULL DEFAULT 1,
  `account_non_locked` TINYINT NOT NULL DEFAULT 1,
  `credentials_non_expired` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`chapter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`chapter` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`description` MEDIUMTEXT NULL,
  `owner_id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`theme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `chapter_id` INT NOT NULL,
  `owner_id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL,
  `descriprion` MEDIUMTEXT NULL,
  `level` TINYINT(1) NULL DEFAULT 1,
  `limit_members` TINYINT(4) NULL DEFAULT 0,
  `active` TINYINT NOT NULL DEFAULT 1,
  `donate_type` VARCHAR(45) NULL,
  `amount` DECIMAL(17,2) NULL DEFAULT 0,
  `theme_id` INT NOT NULL,
  `owner_id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`lesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`lesson` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL,
  `content` LONGTEXT NULL,
  `type` VARCHAR(45) NOT NULL,
  `level` TINYINT(1) NOT NULL DEFAULT 1,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  `course_id` INT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_date` TIMESTAMP NULL,
  `period` TINYINT(2) NULL,
  `lesson_id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`feedback` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT NULL,
  `posted_date` TIMESTAMP NULL,
  `lesson_id` INT NULL,
  `course_id` INT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`user_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`user_course` (
 `user_id` INT NOT NULL,
 `course_id` INT NOT NULL,
 PRIMARY KEY (`user_id`, `course_id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`donate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`donate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` TIMESTAMP NULL,
  `donation` DECIMAL(17,2) NULL,
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `i_share`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `i_share`.`user_role` (
 `user_id` INT NOT NULL,
 `role_id` INT NOT NULL,
 PRIMARY KEY (`user_id`, `role_id`))
  ENGINE = InnoDB;
