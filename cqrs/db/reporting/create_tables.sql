DROP TABLE IF EXISTS `catalogs`;
CREATE TABLE `catalogs` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`version` int(11) NOT NULL,
`uuid` varchar(255) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`version` int(11) NOT NULL,
`isbn` varchar(255) DEFAULT NULL,
`title` varchar(255) DEFAULT NULL,
`author` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
