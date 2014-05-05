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
  `accountLocked` TINYINT(1) NULL COMMENT 'Whether the account is locked (1=True, 0=False).  Never unlocked!',
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
  `id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
  `beginDate` DATETIME NULL COMMENT 'When the game started',
  `endDate` DATETIME NULL COMMENT 'When game finished',
  `turn` INT(10) unsigned NOT NULL COMMENT 'The game turn.  Advance so that players can act.  Initialize to zero, meaning the game has not started.',
  `phase` INT(10) unsigned NOT NULL COMMENT 'The game phase.  1=Action, 2=Buy, 3=Clean-up.',
  `completed` TINYINT(1) NULL COMMENT 'Whether the game completed normally (1=True, 0=False)',
  `winnerId` BIGINT(20) unsigned NOT NULL COMMENT 'Winner ID is the player that won the game (null during game)...Manual JPA connection to Player',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `Players` (
  `id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Player id is unique for each game',
  `gameStateId` BIGINT(20) unsigned NOT NULL COMMENT 'GameState id is unique for each game',
  `userId` INT(10) unsigned NOT NULL COMMENT 'User id is unique for each game',
  `turn` INT(10) unsigned NOT NULL COMMENT 'The player turn order (1=first, 2= second).',
  `actionCount` INT(10) unsigned NOT NULL COMMENT 'Action count',
  `buyCount` INT(10) unsigned NOT NULL COMMENT 'Buy count',
  `coinCount` INT(10) unsigned NOT NULL COMMENT 'Coin count, including coins in hand + bonus coins - coins spent',
  PRIMARY KEY (`id`),
  key `gameState_idx` (`gameStateId`),
  key `user_idx` (`userId`),
  CONSTRAINT FK_USER_ID_FOR_PLAYERS FOREIGN KEY (`userId`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_GAMESTATE_ID_FOR_PLAYERS FOREIGN KEY (`gameStateId`) REFERENCES `GameStates` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `Cards` (
  `id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Card id is unique for each game',
  `gameStateId` BIGINT(20) unsigned NOT NULL COMMENT 'GameState id is unique for each game',
  `playerId` BIGINT(20) unsigned NULL COMMENT 'Player id is unique for each game (Set to NULL for an unassigned card)',
  `location` INT(10) unsigned NOT NULL COMMENT 'Location (e.g. deck=1, in hand=2, discard=3)',
  `cardType` INT(10) unsigned NOT NULL COMMENT 'Type of card (e.g. mine, gold, etc)',
  PRIMARY KEY (`id`),
  key `gameState_idx` (`gameStateId`),
  key `player_idx` (`playerId`),
  CONSTRAINT FK_PLAYER_ID_FOR_CARDS FOREIGN KEY (`playerId`) REFERENCES `Players` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_GAMESTATE_ID_FOR_CARDS FOREIGN KEY (`gameStateId`) REFERENCES `GameStates` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `CardEvents` (
  `id` BIGINT(20) unsigned NOT NULL COMMENT 'Card Event id is unique for each card action',
  `eventDate` DATETIME NULL COMMENT 'When the event occurred',
  `cardType` INT(10) unsigned NOT NULL COMMENT 'Card type for the card played (e.g. woodcutter = 109)',
  `gameStateId` BIGINT(20) unsigned NOT NULL COMMENT 'GameState id is unique for each game',
  `playerId` BIGINT(20) unsigned NULL COMMENT 'Destination Player id',
  `location` INT(10) unsigned NOT NULL COMMENT 'Destination Location (e.g. deck=1, in hand=2, discard=3)',
  PRIMARY KEY (`id`)
  -- key `card_idx` (`cardId`),
  -- key `player_idx` (`playerId`),
  -- CONSTRAINT FK_CARD_ID_FOR_CARDEVENTS FOREIGN KEY (`cardId`) REFERENCES `Cards` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  -- CONSTRAINT FK_PLAYER_ID_FOR_CARDEVENTS FOREIGN KEY (`playerId`) REFERENCES `Players` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE TABLE IF NOT EXISTS `FailedLogins` (
  `id` int(10) unsigned NOT NULL COMMENT 'User id is unique for each card action',
  `lastFailAttemptDate` DATETIME NULL COMMENT 'The last time that a client attempted to login',
  `failCount` INT(10) unsigned NOT NULL COMMENT 'Number of times the client failed to login (always increasing)',
  `dailyFailCount` INT(10) unsigned NOT NULL COMMENT 'Number of times the client failed to login (reset each day)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
