CREATE TABLE IF NOT EXISTS `prueba_tecnica`.`accounts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `client_id` BIGINT NOT NULL,
  `account_number` VARCHAR(255) NULL,
  `account_type` VARCHAR(255) NULL,
  `initial_balance` DECIMAL(10,2) NULL,
  `status` INT NULL,
  PRIMARY KEY (`id`));

  ALTER TABLE `prueba_tecnica`.`accounts`
  ADD UNIQUE INDEX `account_number_UNIQUE` (`account_number` ASC) VISIBLE;
  ;