# MySQL-Front 3.2  (Build 4.15)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET CHARACTER SET 'utf8' */;

CREATE DATABASE `ecobill` /*!40100 DEFAULT CHARACTER SET utf8 */;USE `ecobill`;

CREATE TABLE `base_address` (
  `ID` bigint(20) NOT NULL auto_increment,
  `STREET` varchar(255) NOT NULL default '',
  `ZIP_CODE` varchar(255) NOT NULL default '',
  `CITY` varchar(255) NOT NULL default '',
  `COUNTY_KEY` varchar(255) default NULL,
  `COUNTRY_KEY` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_address` VALUES (6,'Rheingutstraﬂe 32 / 524','78462','Konstanz','de-bw','DE');

CREATE TABLE `base_article` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ARTICLE_NUMBER` varchar(255) NOT NULL default '',
  `UNIT_ID` bigint(20) default NULL,
  `PRICE` double NOT NULL default '0',
  `IN_STOCK` double NOT NULL default '0',
  `BUNDLE_CAPACITY` double default NULL,
  `BUNDLE_UNIT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ARTICLE_NUMBER` (`ARTICLE_NUMBER`),
  KEY `FK7FD39D68E5FDBA65` (`BUNDLE_UNIT_ID`),
  KEY `FK7FD39D68DAFE8522` (`UNIT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_article` VALUES (26,'000001',1,2.95,0,5,27);
INSERT INTO `base_article` VALUES (27,'000002',1,1.95,0,500,25);

CREATE TABLE `base_article_description` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DESCRIPTION` text NOT NULL,
  `SYSTEM_LOCALE_KEY` varchar(255) default NULL,
  `ARTICLE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK90003785FBC7C981` (`ARTICLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_article_description` VALUES (55,'Weizenmehl','de_DE',26);
INSERT INTO `base_article_description` VALUES (56,'Zucker','de_DE',27);

CREATE TABLE `base_banking` (
  `ID` bigint(20) NOT NULL auto_increment,
  `BANK_ESTABLISHMENT` varchar(255) NOT NULL default '',
  `ACCOUNT_NUMBER` varchar(255) NOT NULL default '',
  `BANK_IDENTIFICATION_NUMBER` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_banking` VALUES (6,'','','');

CREATE TABLE `base_bill` (
  `ID` bigint(20) NOT NULL auto_increment,
  `BUSINESS_PARTNER_ID` bigint(20) default NULL,
  `BILL_NUMBER` bigint(20) NOT NULL default '0',
  `BILL_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`ID`),
  KEY `FK3B8E70B592A54740` (`BUSINESS_PARTNER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `base_business_partner` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CUSTOMER_NUMBER` varchar(255) default NULL,
  `COMPANY_TITLE` varchar(255) default NULL,
  `COMPANY_NAME` varchar(255) default NULL,
  `COMPANY_BRANCH` varchar(255) default NULL,
  `FOR_ATTENTION_OF` tinyint(1) default NULL,
  `PERSON_ID` bigint(20) default NULL,
  `ADDRESS_ID` bigint(20) default NULL,
  `BANKING_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK2BB6BED7B6A22533` (`PERSON_ID`),
  KEY `FK2BB6BED733F5BB81` (`BANKING_ID`),
  KEY `FK2BB6BED7292080C1` (`ADDRESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_business_partner` VALUES (6,'000001','','','',0,6,6,6);

CREATE TABLE `base_delivery_order` (
  `ID` bigint(20) NOT NULL auto_increment,
  `BUSINESS_PARTNER_ID` bigint(20) default NULL,
  `DELIVERY_ORDER_NUMBER` varchar(255) NOT NULL default '0',
  `DELIVERY_ORDER_DATE` date NOT NULL default '0000-00-00',
  `CHARACTERISATION_TYPE` varchar(255) NOT NULL default '',
  `PREFIX_FREE_TEXT` text,
  `SUFFIX_FREE_TEXT` text,
  `PREPARED_BILL` tinyint(1) NOT NULL default '0',
  `BILL_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK98DB0A5192A54740` (`BUSINESS_PARTNER_ID`),
  KEY `FK98DB0A51CBBF2233` (`BILL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `base_person` (
  `ID` bigint(20) NOT NULL auto_increment,
  `TITLE_ID` bigint(20) default NULL,
  `ACADEMIC_TITLE_ID` bigint(20) default NULL,
  `FIRSTNAME` varchar(255) default NULL,
  `LASTNAME` varchar(255) default NULL,
  `PHONE` varchar(255) default NULL,
  `FAX` varchar(255) default NULL,
  `EMAIL` varchar(255) default NULL,
  `ADDRESS_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FKA9637083292080C1` (`ADDRESS_ID`),
  KEY `FKA96370837F16C806` (`ACADEMIC_TITLE_ID`),
  KEY `FKA96370837C9C9BCE` (`TITLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_person` VALUES (6,45,NULL,'Roman','R‰dle','(0 75 31) 38 22 27','','roman@raedle.info',NULL);

CREATE TABLE `base_reduplicated_article` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ARTICLE_NUMBER` varchar(255) default NULL,
  `AMOUNT` double NOT NULL default '0',
  `PRICE` double NOT NULL default '0',
  `UNIT` varchar(255) default NULL,
  `DESCRIPTION` varchar(255) NOT NULL default '',
  `DELIVERY_ORDER_ID` bigint(20) default NULL,
  `ARTICLE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK41BAB371A0761D98` (`DELIVERY_ORDER_ID`),
  KEY `FK41BAB371FBC7C981` (`ARTICLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `base_system_country` (
  `ID` bigint(20) NOT NULL auto_increment,
  `SYSTEM_COUNTRY_KEY` varchar(255) NOT NULL default '',
  `SYSTEM_LANGUAGE_KEY` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_system_country` VALUES (1,'DE','de');
INSERT INTO `base_system_country` VALUES (2,'AT','de');
INSERT INTO `base_system_country` VALUES (3,'CH','de');
INSERT INTO `base_system_country` VALUES (4,'UK','en');
INSERT INTO `base_system_country` VALUES (5,'US','en');

CREATE TABLE `base_system_county` (
  `ID` bigint(20) NOT NULL auto_increment,
  `SYSTEM_COUNTY_KEY` varchar(255) NOT NULL default '',
  `SYSTEM_COUNTRY_KEY` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_system_county` VALUES (1,'de-bw','DE');
INSERT INTO `base_system_county` VALUES (2,'de-by','DE');
INSERT INTO `base_system_county` VALUES (3,'de-be','DE');
INSERT INTO `base_system_county` VALUES (4,'de-br','DE');
INSERT INTO `base_system_county` VALUES (5,'de-hb','DE');
INSERT INTO `base_system_county` VALUES (6,'de-hh','DE');
INSERT INTO `base_system_county` VALUES (7,'de-he','DE');
INSERT INTO `base_system_county` VALUES (8,'de-mv','DE');
INSERT INTO `base_system_county` VALUES (9,'de-ni','DE');
INSERT INTO `base_system_county` VALUES (10,'de-nw','DE');
INSERT INTO `base_system_county` VALUES (11,'de-rp','DE');
INSERT INTO `base_system_county` VALUES (12,'de-sl','DE');
INSERT INTO `base_system_county` VALUES (13,'de-sn','DE');
INSERT INTO `base_system_county` VALUES (14,'de-st','DE');
INSERT INTO `base_system_county` VALUES (15,'de-sh','DE');
INSERT INTO `base_system_county` VALUES (16,'de-th','DE');
INSERT INTO `base_system_county` VALUES (17,'at-1','AT');
INSERT INTO `base_system_county` VALUES (18,'at-2','AT');
INSERT INTO `base_system_county` VALUES (19,'at-3','AT');
INSERT INTO `base_system_county` VALUES (20,'at-4','AT');
INSERT INTO `base_system_county` VALUES (21,'at-5','AT');
INSERT INTO `base_system_county` VALUES (22,'at-6','AT');
INSERT INTO `base_system_county` VALUES (23,'at-7','AT');
INSERT INTO `base_system_county` VALUES (24,'at-8','AT');
INSERT INTO `base_system_county` VALUES (25,'at-9','AT');
INSERT INTO `base_system_county` VALUES (26,'ch-zh','CH');
INSERT INTO `base_system_county` VALUES (27,'ch-be','CH');
INSERT INTO `base_system_county` VALUES (29,'ch-lu','CH');
INSERT INTO `base_system_county` VALUES (30,'ch-ur','CH');
INSERT INTO `base_system_county` VALUES (31,'ch-sz','CH');
INSERT INTO `base_system_county` VALUES (32,'ch-ow','CH');
INSERT INTO `base_system_county` VALUES (33,'ch-nw','CH');
INSERT INTO `base_system_county` VALUES (34,'ch-gl','CH');
INSERT INTO `base_system_county` VALUES (35,'ch-zg','CH');
INSERT INTO `base_system_county` VALUES (36,'ch-fr','CH');
INSERT INTO `base_system_county` VALUES (37,'ch-so','CH');
INSERT INTO `base_system_county` VALUES (38,'ch-bs','CH');
INSERT INTO `base_system_county` VALUES (39,'ch-bl','CH');
INSERT INTO `base_system_county` VALUES (40,'ch-sh','CH');
INSERT INTO `base_system_county` VALUES (41,'ch-ar','CH');
INSERT INTO `base_system_county` VALUES (42,'ch-ai','CH');
INSERT INTO `base_system_county` VALUES (43,'ch-sg','CH');
INSERT INTO `base_system_county` VALUES (44,'ch-gr','CH');
INSERT INTO `base_system_county` VALUES (45,'ch-ag','CH');
INSERT INTO `base_system_county` VALUES (46,'ch-tg','CH');
INSERT INTO `base_system_county` VALUES (47,'ch-ti','CH');
INSERT INTO `base_system_county` VALUES (48,'ch-vd','CH');
INSERT INTO `base_system_county` VALUES (49,'ch-vs','CH');
INSERT INTO `base_system_county` VALUES (50,'ch-ne','CH');
INSERT INTO `base_system_county` VALUES (51,'ch-ge','CH');
INSERT INTO `base_system_county` VALUES (52,'ch-ju','CH');

CREATE TABLE `base_system_language` (
  `ID` bigint(20) NOT NULL auto_increment,
  `SYSTEM_LANGUAGE_KEY` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_system_language` VALUES (1,'de');
INSERT INTO `base_system_language` VALUES (2,'en');

CREATE TABLE `base_system_locale` (
  `ID` bigint(20) NOT NULL auto_increment,
  `SYSTEM_LOCALE_KEY` varchar(255) NOT NULL default '',
  `VARIANT` varchar(255) default NULL,
  `SYSTEM_LANGUAGE_KEY` varchar(255) default NULL,
  `SYSTEM_COUNTRY_KEY` varchar(255) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `SYSTEM_LOCALE_KEY` (`SYSTEM_LOCALE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_system_locale` VALUES (1,'de_DE','','de','DE');
INSERT INTO `base_system_locale` VALUES (2,'de_AT','','de','AT');
INSERT INTO `base_system_locale` VALUES (3,'de_CH','','de','CH');
INSERT INTO `base_system_locale` VALUES (4,'en_UK','','en','UK');
INSERT INTO `base_system_locale` VALUES (5,'en_US','','en','US');

CREATE TABLE `base_system_unit` (
  `ID` bigint(20) NOT NULL auto_increment,
  `UNIT_KEY` varchar(255) NOT NULL default '',
  `CATEGORY` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `base_system_unit` VALUES (1,'amount.bag','unit');
INSERT INTO `base_system_unit` VALUES (2,'amount.piece','unit');
INSERT INTO `base_system_unit` VALUES (3,'weight.mg','unit');
INSERT INTO `base_system_unit` VALUES (4,'weight.g','unit');
INSERT INTO `base_system_unit` VALUES (5,'weight.kg','unit');
INSERT INTO `base_system_unit` VALUES (6,'weight.t','unit');
INSERT INTO `base_system_unit` VALUES (7,'volume.ml','unit');
INSERT INTO `base_system_unit` VALUES (8,'volume.cl','unit');
INSERT INTO `base_system_unit` VALUES (9,'volume.l','unit');
INSERT INTO `base_system_unit` VALUES (10,'scale.mm','unit');
INSERT INTO `base_system_unit` VALUES (11,'scale.cm','unit');
INSERT INTO `base_system_unit` VALUES (12,'scale.dm','unit');
INSERT INTO `base_system_unit` VALUES (13,'scale.m','unit');
INSERT INTO `base_system_unit` VALUES (14,'scale.km','unit');
INSERT INTO `base_system_unit` VALUES (15,'scale.mm_quad','unit');
INSERT INTO `base_system_unit` VALUES (16,'scale.cm_quad','unit');
INSERT INTO `base_system_unit` VALUES (17,'scale.dm_quad','unit');
INSERT INTO `base_system_unit` VALUES (18,'scale.m_quad','unit');
INSERT INTO `base_system_unit` VALUES (19,'scale.km_quad','unit');
INSERT INTO `base_system_unit` VALUES (20,'scale.ar','unit');
INSERT INTO `base_system_unit` VALUES (21,'scale.hekta','unit');
INSERT INTO `base_system_unit` VALUES (22,'scale.morgen','unit');
INSERT INTO `base_system_unit` VALUES (23,'amount.bag','bundle_unit');
INSERT INTO `base_system_unit` VALUES (24,'amount.piece','bundle_unit');
INSERT INTO `base_system_unit` VALUES (25,'weight.mg','bundle_unit');
INSERT INTO `base_system_unit` VALUES (26,'weight.g','bundle_unit');
INSERT INTO `base_system_unit` VALUES (27,'weight.kg','bundle_unit');
INSERT INTO `base_system_unit` VALUES (28,'weight.t','bundle_unit');
INSERT INTO `base_system_unit` VALUES (29,'volume.ml','bundle_unit');
INSERT INTO `base_system_unit` VALUES (30,'volume.cl','bundle_unit');
INSERT INTO `base_system_unit` VALUES (31,'volume.l','bundle_unit');
INSERT INTO `base_system_unit` VALUES (32,'scale.mm','bundle_unit');
INSERT INTO `base_system_unit` VALUES (33,'scale.cm','bundle_unit');
INSERT INTO `base_system_unit` VALUES (34,'scale.dm','bundle_unit');
INSERT INTO `base_system_unit` VALUES (35,'scale.m','bundle_unit');
INSERT INTO `base_system_unit` VALUES (36,'scale.km','bundle_unit');
INSERT INTO `base_system_unit` VALUES (37,'scale.mm_quad','bundle_unit');
INSERT INTO `base_system_unit` VALUES (38,'scale.cm_quad','bundle_unit');
INSERT INTO `base_system_unit` VALUES (39,'scale.dm_quad','bundle_unit');
INSERT INTO `base_system_unit` VALUES (40,'scale.m_quad','bundle_unit');
INSERT INTO `base_system_unit` VALUES (41,'scale.km_quad','bundle_unit');
INSERT INTO `base_system_unit` VALUES (42,'scale.ar','bundle_unit');
INSERT INTO `base_system_unit` VALUES (43,'scale.hekta','bundle_unit');
INSERT INTO `base_system_unit` VALUES (44,'scale.morgen','bundle_unit');
INSERT INTO `base_system_unit` VALUES (45,'mr','title');
INSERT INTO `base_system_unit` VALUES (46,'mrs','title');
INSERT INTO `base_system_unit` VALUES (47,'dr','academic_title');
INSERT INTO `base_system_unit` VALUES (48,'prof_dr','academic_title');

ALTER TABLE `base_article`
  ADD FOREIGN KEY (`UNIT_ID`) REFERENCES `base_system_unit` (`ID`),
  ADD FOREIGN KEY (`BUNDLE_UNIT_ID`) REFERENCES `base_system_unit` (`ID`);


ALTER TABLE `base_article_description`
  ADD FOREIGN KEY (`ARTICLE_ID`) REFERENCES `base_article` (`ID`);


ALTER TABLE `base_bill`
  ADD FOREIGN KEY (`BUSINESS_PARTNER_ID`) REFERENCES `base_business_partner` (`ID`);


ALTER TABLE `base_business_partner`
  ADD FOREIGN KEY (`ADDRESS_ID`) REFERENCES `base_address` (`ID`),
  ADD FOREIGN KEY (`BANKING_ID`) REFERENCES `base_banking` (`ID`),
  ADD FOREIGN KEY (`PERSON_ID`) REFERENCES `base_person` (`ID`);


ALTER TABLE `base_delivery_order`
  ADD FOREIGN KEY (`BUSINESS_PARTNER_ID`) REFERENCES `base_business_partner` (`ID`),
  ADD FOREIGN KEY (`BILL_ID`) REFERENCES `base_bill` (`ID`);


ALTER TABLE `base_person`
  ADD FOREIGN KEY (`TITLE_ID`) REFERENCES `base_system_unit` (`ID`),
  ADD FOREIGN KEY (`ADDRESS_ID`) REFERENCES `base_address` (`ID`),
  ADD FOREIGN KEY (`ACADEMIC_TITLE_ID`) REFERENCES `base_system_unit` (`ID`);


ALTER TABLE `base_reduplicated_article`
  ADD FOREIGN KEY (`ARTICLE_ID`) REFERENCES `base_article` (`ID`),
  ADD FOREIGN KEY (`DELIVERY_ORDER_ID`) REFERENCES `base_delivery_order` (`ID`);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
