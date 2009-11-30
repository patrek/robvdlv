DROP TABLE IF EXISTS `event_eventproviders`;
CREATE TABLE `event_eventproviders` (
`id` varchar(255) NOT NULL,
`version` int(11) NOT NULL,
`type` varchar(255) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `event_events`;
CREATE TABLE `event_events` (
`sequence` int(11) NOT NULL AUTO_INCREMENT,
`occurred_at` datetime NOT NULL,
`eventprovider_id` varchar(255) NOT NULL,
`data` varbinary(8192) NOT NULL,
PRIMARY KEY (`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reporting_catalogs`;
CREATE TABLE `reporting_catalogs` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`version` int(11) NOT NULL,
`uuid` varchar(255) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reporting_books`;
CREATE TABLE `reporting_books` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`catalog_id` bigint(20) NOT NULL,
`version` int(11) NOT NULL,
`isbn` varchar(255) DEFAULT NULL,
`title` varchar(255) DEFAULT NULL,
`author` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `book_catalog` (`catalog_id`),
CONSTRAINT `book_catalog` FOREIGN KEY (`catalog_id`) REFERENCES `reporting_catalogs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
