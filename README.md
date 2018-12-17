CREATE TABLE IF NOT EXISTS `mydb`.`trains` (
  `id` INT NOT NULL,
  `seats_number` INT NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `mydb`.`train_stations` (
  `id` INT NOT NULL,
  `train_id` INT NOT NULL,
  `station_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_train_stations_trains_idx` (`train_id` ASC) VISIBLE,
  INDEX `fk_train_stations_stations1_idx` (`station_id` ASC) VISIBLE,
  CONSTRAINT `fk_train_stations_trains`
    FOREIGN KEY (`train_id`)
    REFERENCES `mydb`.`trains` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_train_stations_stations1`
    FOREIGN KEY (`station_id`)
    REFERENCES `mydb`.`stations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `mydb`.`stations` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `mydb`.`passengers` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `birthdate` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `mydb`.`tickets` (
  `id` INT NOT NULL,
  `passenger_id` INT NOT NULL,
  `schedule_from` INT ZEROFILL NOT NULL,
  `schedule_to` INT NOT NULL,
  PRIMARY KEY (`id`, `passenger_id`),
  INDEX `fk_tickets_passengers1_idx` (`passenger_id` ASC) VISIBLE,
  INDEX `fk_tickets_schedule1_idx` (`schedule_from` ASC) VISIBLE,
  INDEX `fk_tickets_schedule2_idx` (`schedule_to` ASC) VISIBLE,
  CONSTRAINT `fk_tickets_passengers1`
    FOREIGN KEY (`passenger_id`)
    REFERENCES `mydb`.`passengers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tickets_schedule1`
    FOREIGN KEY (`schedule_from`)
    REFERENCES `mydb`.`schedule` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tickets_schedule2`
    FOREIGN KEY (`schedule_to`)
    REFERENCES `mydb`.`schedule` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `mydb`.`schedule` (
  `id` INT NOT NULL,
  `train_id` INT NOT NULL,
  `station_id` INT NOT NULL,
  `departure_at` DATETIME NULL,
  `arrival_at` DATETIME NULL,
  `seats_occupied` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_schedule_trains1_idx` (`train_id` ASC) VISIBLE,
  INDEX `fk_schedule_stations1_idx` (`station_id` ASC) VISIBLE,
  CONSTRAINT `fk_schedule_trains1`
    FOREIGN KEY (`train_id`)
    REFERENCES `mydb`.`trains` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_schedule_stations1`
    FOREIGN KEY (`station_id`)
    REFERENCES `mydb`.`stations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB