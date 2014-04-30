delimiter $$

CREATE DATABASE IF NOT EXISTS `gamewebsite` /*!40100 DEFAULT CHARACTER SET utf8 */$$

use `gamewebsite`$$

CREATE TABLE IF NOT EXISTS `SEQUENCE_TABLE` (
  `SEQ_NAME` varchar(20) NOT NULL DEFAULT '' COMMENT 'For Table ID Generation',
  `SEQ_COUNT` int(11) NOT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

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

CREATE TABLE IF NOT EXISTS `GameStates` (
  `id` BIGINT(20) unsigned NOT NULL,
  `beginDate` DATETIME NULL COMMENT 'When the game started',
  `endDate` DATETIME NULL COMMENT 'When game finished',
  `turn` INT(10) unsigned NOT NULL COMMENT 'The game turn.  Advance so that players can act.  Initialize to zero, meaning the game has not started.',
  `completed` TINYINT(1) NULL COMMENT 'Whether the game completed normally (1=True, 0=False)',
  `winnerId` BIGINT(20) unsigned NOT NULL COMMENT 'Winner ID is the player that won the game (null during game)...Manual JPA connection to Player',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `Players` (
  `id` BIGINT(20) unsigned NOT NULL COMMENT 'Player id is unique for each game',
  `gameStateId` BIGINT(20) unsigned NOT NULL COMMENT 'GameState id is unique for each game',
  `userId` INT(10) unsigned NOT NULL COMMENT 'User id is unique for each game',
  `turn` INT(10) unsigned NOT NULL COMMENT 'The player turn.  Player can only act when the game turn is equal to the player turn.  Initialize to 1.',
  `actionCount` INT(10) unsigned NOT NULL COMMENT 'Action count',
  `buyCount` INT(10) unsigned NOT NULL COMMENT 'Buy count',
  `coinCount` INT(10) unsigned NOT NULL COMMENT 'Coin count',
  `treasure` INT(10) unsigned NOT NULL COMMENT 'Treasure',
  PRIMARY KEY (`id`),
  key `gameState_idx` (`gameStateId`),
  key `user_idx` (`userId`),
  CONSTRAINT FK_USER_ID_FOR_PLAYERS FOREIGN KEY (`userId`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_GAMESTATE_ID_FOR_PLAYERS FOREIGN KEY (`gameStateId`) REFERENCES `GameStates` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `Cards` (
  `id` BIGINT(20) unsigned NOT NULL COMMENT 'Card id is unique for each game',
  `gameStateId` BIGINT(20) unsigned NOT NULL COMMENT 'GameState id is unique for each game',
  `playerId` BIGINT(20) unsigned NULL COMMENT 'Player id is unique for each game (Set to NULL for an unassigned card)',
  `location` INT(10) unsigned NOT NULL COMMENT 'Location (e.g. discard, in hand, deck.)',
  `cardType` INT(10) unsigned NOT NULL COMMENT 'Type of card (e.g. mine, gold, etc)',
  PRIMARY KEY (`id`),
  key `gameState_idx` (`gameStateId`),
  key `player_idx` (`playerId`),
  CONSTRAINT FK_PLAYER_ID_FOR_CARDS FOREIGN KEY (`playerId`) REFERENCES `Players` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_GAMESTATE_ID_FOR_CARDS FOREIGN KEY (`gameStateId`) REFERENCES `GameStates` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$
