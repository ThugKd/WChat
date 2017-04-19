CREATE TABLE `tb_user` (
  `uphone` varchar(11) NOT NULL,
  `upassword` varchar(20)  NOT NULL,
  `unick` varchar(20) NOT NULL DEFAULT 'wchat',
  `uavatar` varchar(40) DEFAULT '../../../../images/ai.jpg',
  `usex` tinyint(4) NOT NULL DEFAULT '0',
  `uage` smallint(6) NOT NULL DEFAULT '1',
  `uisonline` tinyint(4) DEFAULT '0',
  `uaddtime` datetime DEFAULT NULL,
  PRIMARY KEY (`uphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `tb_buddy` (
  `uphone` varchar(11) NOT NULL,
  `bphone` varchar(11)  NOT NULL,
  `baddtime` datetime DEFAULT NULL,
  KEY `tb_buddy_bphone_fk` (`uphone`),
  CONSTRAINT `tb_buddy_bphone_fk` FOREIGN KEY (`uphone`) REFERENCES `tb_user` (`uphone`),
  CONSTRAINT `tb_buddy_uphone_fk` FOREIGN KEY (`uphone`) REFERENCES `tb_user` (`uphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `tb_group_member` (
  `gaccount` smallint(6) DEFAULT NULL,
  `uphone` varchar(11) DEFAULT NULL,
  KEY `tb_group_member_acc_fk` (`gaccount`),
  KEY `tb_group_member_phone_fk` (`uphone`),
  CONSTRAINT `tb_group_member_acc_fk` FOREIGN KEY (`gaccount`) REFERENCES `tb_group` (`gaccount`),
  CONSTRAINT `tb_group_member_phone_fk` FOREIGN KEY (`uphone`) REFERENCES `tb_user` (`uphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `tb_group` (
  `gaccount` smallint(6) NOT NULL AUTO_INCREMENT,
  `gnick` varchar(20) NOT NULL DEFAULT 'wchatGroup',
  `gmanager` varchar(11) NOT NULL,
  `gcount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`gaccount`),
  KEY `tb_group_manager_fk` (`gmanager`),
  CONSTRAINT `tb_group_manager_fk` FOREIGN KEY (`gmanager`) REFERENCES `tb_user` (`uphone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8