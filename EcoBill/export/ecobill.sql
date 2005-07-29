# MySQL-Front 3.2  (Build 4.15)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET CHARACTER SET 'utf8' */;

# Host: localhost    Database: ecobill
# ------------------------------------------------------
# Server version 4.1.11-nt

USE `ecobill`;

#
# Dumping data for table base_address
#

INSERT INTO `base_address` (`ID`,`STREET`,`ZIP_CODE`,`CITY`,`COUNTY`,`COUNTRY`) VALUES (1,'Obere Laube 51','78462','Konstanz','Baden-Württemberg','Deutschland');

#
# Dumping data for table base_article
#

INSERT INTO `base_article` (`ID`,`UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_UNIT_KEY`) VALUES (1,'amount.bag',24.95,43,25,'weight.kg');
INSERT INTO `base_article` (`ID`,`UNIT_KEY`,`PRICE`,`IN_STOCK`,`BUNDLE_CAPACITY`,`BUNDLE_UNIT_KEY`) VALUES (2,'amount.piece',9.99,129,1,'volume.l');

#
# Dumping data for table base_article_description
#

INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (1,'Fliesurit Flex (deutsch)','de_DE',1);
INSERT INTO `base_article_description` (`ID`,`DESCRIPTION`,`SYSTEM_LOCALE_KEY`,`ARTICLE_ID`) VALUES (2,'Fliesurit Flex (english)','en_UK',1);

#
# Dumping data for table base_banking
#

INSERT INTO `base_banking` (`ID`,`BANK_ESTABLISHMENT`,`ACCOUNT_NUMBER`,`BANK_IDENTIFICATION_NUMBER`) VALUES (1,'Sparkasse Bodensee','123456','65351050');

#
# Dumping data for table base_bill
#

INSERT INTO `base_bill` (`ID`,`BUSINESS_PARTNER_ID`,`BILL_NUMBER`,`BILL_DATE`) VALUES (1,1,9876,'2005-07-30');

#
# Dumping data for table base_business_partner
#

INSERT INTO `base_business_partner` (`ID`,`COMPANY_TITLE_KEY`,`COMPANY_NAME`,`PERSON_ID`,`ADDRESS_ID`,`BANKING_ID`) VALUES (1,'firm','JF 08/15',1,1,1);

#
# Dumping data for table base_delivery_order
#

INSERT INTO `base_delivery_order` (`ID`,`BUSINESS_PARTNER_ID`,`DELIVERY_ORDER_NUMBER`,`DELIVERY_ORDER_DATE`,`CHARACTERISATION_TYPE`,`PREPARED_BILL`,`BILL_ID`) VALUES (1,1,2,'2005-07-28','DELIVERY_ORDER',1,1);

#
# Dumping data for table base_person
#

INSERT INTO `base_person` (`ID`,`TITLE_KEY`,`ACADEMIC_TITLE_KEY`,`FIRSTNAME`,`LASTNAME`,`PHONE`,`FAX`,`EMAIL`,`ADDRESS_ID`) VALUES (1,'mr',NULL,'Sebastian','Gath',NULL,NULL,'sgath@gmx.de',1);

#
# Dumping data for table base_reduplicated_article
#

INSERT INTO `base_reduplicated_article` (`ID`,`AMOUNT`,`PRICE`,`DESCRIPTION`,`DELIVERY_ORDER_ID`) VALUES (1,2.25,9.95,'Irgendein Artikel',1);

#
# Dumping data for table base_system_locale
#

INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (1,'de_DE','de','DE',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (2,'de_AT','de','AT',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (3,'de_CH','de','CH',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (4,'en_UK','en','UK',NULL);
INSERT INTO `base_system_locale` (`ID`,`SYSTEM_LOCALE_KEY`,`LANGUAGE`,`COUNTRY`,`VARIANT`) VALUES (5,'en_US','en','US',NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
