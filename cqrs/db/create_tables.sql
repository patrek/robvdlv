DROP TABLE IF EXISTS `eventproviders`;
CREATE TABLE `eventproviders` (
  `id` varchar(255) NOT NULL,
  `version` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `sequence` int(11) NOT NULL AUTO_INCREMENT,
  `occurred_at` datetime NOT NULL,
  `eventprovider_id` varchar(255) NOT NULL,
  `data` varbinary(8192) NOT NULL,
  PRIMARY KEY (`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`isbn` varchar(255) DEFAULT NULL,
`title` varchar(255) DEFAULT NULL,
`author` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
