# MySQL-Front 3.2  (Build 4.15)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET CHARACTER SET 'utf8' */;

USE `ecobill`;

INSERT INTO `base_address` (`ID`,`STREET`,`ZIP_CODE`,`CITY`,`COUNTY`,`COUNTRY`) VALUES (1,'Obere Laube 51','78462','Konstanz','Baden-Württemberg','Deutschland');

INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (1,'08/15','amount.bag',24.95,43,25,'weight.kg');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (2,'14+4','amount.piece',9.99,129,1,'volume.l');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (3,'90-60-90','amount.piece',125.5,2,20,'amount.piece');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (4,'412-q-12','amount.bag',14.95,101,50,'weight.g');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (5,'48965b','amount.bag',45.99,58,25,'weight.kg');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (6,'48965a','amount.piece',44.95,102,5,'weight.kg');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (7,'48966','amount.piece',12.95,25105,250,'weight.kg');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (8,'68966a','amount.piece',9.95,160,50,'volume.ml');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (9,'68966b','amount.piece',2.99,154,25,'volume.ml');
INSERT INTO `base_article` (`ID`,`ARTICLE_NUMBER`,`SYSTEM_UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_SYSTEM_UNIT_KEY`) VALUES (10,'68966c','amount.piece',1.99,6548,500,'volume.ml');

INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (1,'Fliesurit Flex (deutsch)','de_DE',1);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (2,'Fliesurit Flex (english)','en_UK',1);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (3,'E10 (unsere Europastrasse)','de_DE',3);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (4,'Juckpulver (sehr stark)','de_DE',4);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (5,'Fliesurit Plus Ultrastarker Halt','de_DE',5);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (6,'Fliesurit Plus Ultrastarker Halt (Mini)','de_DE',6);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (7,'Haarschampooo die 2.','de_DE',7);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (8,'Braeungungscreme','de_DE',8);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (9,'Elvital de Creation Modual','de_DE',9);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (10,'Reinigungscrem','de_DE',10);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (11,'No Description','de_DE',2);

INSERT INTO `base_banking` (`ID`,`BANK_ESTABLISHMENT`,`ACCOUNT_NUMBER`,`BANK_IDENTIFICATION_NUMBER`) VALUES (1,'Sparkasse Bodensee','123456','65351050');

INSERT INTO `base_bill` (`ID`,`BUSINESS_PARTNER_ID`,`BILL_NUMBER`,`BILL_DATE`) VALUES (1,1,9876,'2005-07-30');

INSERT INTO `base_business_partner` (`ID`,`COMPANY_TITLE_KEY`,`COMPANY_NAME`,`PERSON_ID`,`ADDRESS_ID`,`BANKING_ID`) VALUES (1,'firm','JF 08/15',1,1,1);


INSERT INTO `base_person` (`ID`,`TITLE_KEY`,`ACADEMIC_TITLE_KEY`,`FIRSTNAME`,`LASTNAME`,`PHONE`,`FAX`,`EMAIL`,`ADDRESS_ID`) VALUES (1,'mr',NULL,'Sebastian','Gath',NULL,NULL,'sgath@gmx.de',1);


INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (1,'de_DE','de','DE',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (2,'de_AT','de','AT',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (3,'de_CH','de','CH',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (4,'en_UK','en','UK',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (5,'en_US','en','US',NULL);

INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (1,'amount.bag');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (2,'amount.piece');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (3,'weight.mg');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (4,'weight.g');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (5,'weight.kg');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (6,'weight.t');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (7,'volume.ml');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (8,'volume.cl');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (9,'volume.l');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (10,'scale.mm');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (11,'scale.cm');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (12,'scale.dm');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (13,'scale.m');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (14,'scale.km');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (15,'scale.mm_quad');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (16,'scale.cm_quad');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (17,'scale.dm_quad');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (18,'scale.m_quad');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (19,'scale.km_quad');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (20,'scale.ar');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (21,'scale.hekta');
INSERT INTO `base_system_unit` (`ID`,`UNIT_KEY`) VALUES (22,'scale.morgen');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
