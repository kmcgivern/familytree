CREATE TABLE `tblperson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `middleName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) NOT NULL,
  `birthDate` datetime NOT NULL,
  `dateDied` datetime DEFAULT NULL,
  `isMale` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `firstName_idx` (`firstName`),
  KEY `lastname_idx` (`lastName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
