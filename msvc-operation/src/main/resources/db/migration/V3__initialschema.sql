CREATE TABLE IF NOT EXISTS `prueba_tecnica`.`movements` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT NOT NULL,
  `registration_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `movement_type` VARCHAR(255) NULL,
  `balance` DECIMAL(10,2) NULL,
  `amount` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`))
