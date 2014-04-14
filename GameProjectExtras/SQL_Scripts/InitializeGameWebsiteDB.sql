delimiter $$

CREATE DATABASE IF NOT EXISTS `gamewebsite` /*!40100 DEFAULT CHARACTER SET utf8 */$$

use `gamewebsite`$$

CREATE TABLE IF NOT EXISTS `UserRoles` (
  `id` varchar(10) NOT NULL,
  `description` varchar(45) NOT NULL COMMENT 'A description for the User Role',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

INSERT INTO `UserRoles` (`id`,`description`) VALUES ('admin','administrator')$$
INSERT INTO `UserRoles` (`id`,`description`) VALUES ('guest','unregistered user')$$
INSERT INTO `UserRoles` (`id`,`description`) VALUES ('player','registered user')$$

CREATE TABLE IF NOT EXISTS `Users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL COMMENT 'User''s E-mail Address.',
  `password` varchar(128) NOT NULL COMMENT 'Salted hash representation of the user''s password',
  `role` varchar(10) DEFAULT NULL,
  `salutation` varchar(8) DEFAULT NULL COMMENT 'Mr., Mrs., Dr., etc.',
  `firstname` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `lastname` varchar(32) DEFAULT NULL,
  `aboutme` text,
  `last_active` datetime DEFAULT NULL COMMENT 'This is the last date/time that the user was active in the game.',
  `last_password_change` date DEFAULT NULL COMMENT 'This is the last date that the user changed his password.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_idx` (`role`),
  CONSTRAINT `role` FOREIGN KEY (`role`) REFERENCES `UserRoles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `GameResults` (
  `id` INT(10) unsigned NOT NULL,
  `winner` INT(10) unsigned NULL COMMENT 'Player who won.',
  `loser` INT(10) unsigned NULL COMMENT 'Player who lost.',
  `beginDate` DATETIME NULL COMMENT 'When the game started',
  `endDate` DATETIME NULL COMMENT 'When game finished',
  `completed` TINYINT(1) NULL COMMENT 'Whether the game completed normally (1=True, 0=False)',
  PRIMARY KEY (`id`),
  key `winner_idx` (`winner`),
  key `loser_idx` (`loser`),
  CONSTRAINT FK_WINNER_ID FOREIGN KEY (`winner`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_LOSER_ID FOREIGN KEY (`loser`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `GamePurchases` (
  `id` INT(10) unsigned NOT NULL,
  `package` VARCHAR(45) NULL COMMENT 'Name of the package that the player purchased.',
  `purchaseDate` DATETIME NOT NULL COMMENT 'When transaction was executed.',
  `playerId` INT(10) unsigned NOT NULL COMMENT 'Player who made the purchase.',
  `coinsPurchased` VARCHAR(45) NOT NULL COMMENT 'How many game coins did the player purchase.',
  `price` INT UNSIGNED NOT NULL COMMENT 'Cost of the transaction (USD).',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id`),
  key `player_idx` (`playerId`),
  CONSTRAINT FK_PLAYER_ID FOREIGN KEY (`playerId`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$