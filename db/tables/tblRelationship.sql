CREATE TABLE `tblrelationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personID1` int(11) NOT NULL,
  `personID2` int(11) NOT NULL,
  `relationshipType` varchar(45) NOT NULL,
  `dateOfMarriage` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `person1_idx` (`personID1`),
  KEY `person2_idx` (`personID2`),
  CONSTRAINT `person1fk` FOREIGN KEY (`personID1`) REFERENCES `tblperson` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `person2fk` FOREIGN KEY (`personID2`) REFERENCES `tblperson` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
