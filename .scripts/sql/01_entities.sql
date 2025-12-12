CREATE DATABASE IF NOT EXISTS `irsi`
  DEFAULT CHARACTER SET = 'utf8mb4'
  DEFAULT COLLATE = 'utf8mb4_0900_ai_ci'
  DEFAULT ENCRYPTION = 'N';

USE `irsi`;

CREATE TABLE IF NOT EXISTS `dim_currencies` (
  `code` CHAR(3) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `symbol` CHAR(10),
  PRIMARY KEY (`code`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `dim_suppliers` (
  `id` SMALLINT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(500),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `dim_properties` (
  `eircode` CHAR(7) NOT NULL,
  `county` VARCHAR(50) NOT NULL,
  `address` VARCHAR(500),
  `type` VARCHAR(255),
  PRIMARY KEY (`eircode`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `dim_postal_towns` (
  `routing_key` CHAR(3) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `county` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`routing_key`, `name`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `fact_property_sales` (
  `id` BIGINT AUTO_INCREMENT NOT NULL,
  `property_eircode` CHAR(7) NOT NULL,
  `sold_at` DATE NOT NULL,
  `price` DECIMAL(20, 2) NOT NULL,
  `currency_code` CHAR(3) NOT NULL,
  `supplier_id` SMALLINT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY `ix_fact_property_sales__property__fk` (`property_eircode`)
    REFERENCES `dim_properties` (`eircode`)
    ON DELETE CASCADE,
  FOREIGN KEY `ix_fact_property_sales__currency__fk` (`currency_code`)
    REFERENCES `dim_currencies` (`code`)
    ON DELETE CASCADE,
  FOREIGN KEY `ix_fact_property_sales__supplier__fk` (`supplier_id`)
    REFERENCES `dim_suppliers` (`id`)
    ON DELETE CASCADE,
  UNIQUE KEY `ix_fact_property_sales__property_sold__uq`
    (`sold_at`, `property_eircode`)
) ENGINE = InnoDB;
