alter table BASE_ARTICLE drop foreign key FK7FD39D68E5FDBA65;
alter table BASE_ARTICLE drop foreign key FK7FD39D68DAFE8522;
alter table BASE_ARTICLE_DESCRIPTION drop foreign key FK90003785FBC7C981;
alter table BASE_BILL drop foreign key FK3B8E70B592A54740;
alter table BASE_BUSINESS_PARTNER drop foreign key FK2BB6BED7B6A22533;
alter table BASE_BUSINESS_PARTNER drop foreign key FK2BB6BED733F5BB81;
alter table BASE_BUSINESS_PARTNER drop foreign key FK2BB6BED7292080C1;
alter table BASE_DELIVERY_ORDER drop foreign key FK98DB0A5192A54740;
alter table BASE_DELIVERY_ORDER drop foreign key FK98DB0A51CBBF2233;
alter table BASE_PERSON drop foreign key FKA96370837F16C806;
alter table BASE_PERSON drop foreign key FKA96370837C9C9BCE;
alter table BASE_PERSON drop foreign key FKA9637083292080C1;
alter table BASE_REDUPLICATED_ARTICLE drop foreign key FK41BAB371A0761D98;
drop table if exists BASE_ADDRESS;
drop table if exists BASE_ARTICLE;
drop table if exists BASE_ARTICLE_DESCRIPTION;
drop table if exists BASE_BANKING;
drop table if exists BASE_BILL;
drop table if exists BASE_BUSINESS_PARTNER;
drop table if exists BASE_DELIVERY_ORDER;
drop table if exists BASE_NUMBER_SEQUENCE;
drop table if exists BASE_PERSON;
drop table if exists BASE_REDUPLICATED_ARTICLE;
drop table if exists BASE_SYSTEM_COUNTRY;
drop table if exists BASE_SYSTEM_COUNTY;
drop table if exists BASE_SYSTEM_LANGUAGE;
drop table if exists BASE_SYSTEM_LOCALE;
drop table if exists BASE_SYSTEM_MESSAGE;
drop table if exists BASE_SYSTEM_UNIT;
create table BASE_ADDRESS (
    ID bigint not null auto_increment,
    STREET varchar(255) not null,
    ZIP_CODE varchar(255) not null,
    CITY varchar(255) not null,
    COUNTRY_KEY varchar(255),
    COUNTY_KEY varchar(255),
    primary key (ID)
) type=InnoDB;
create table BASE_ARTICLE (
    ID bigint not null auto_increment,
    ARTICLE_NUMBER varchar(255) not null unique,
    UNIT_ID bigint,
    PRICE double precision not null,
    IN_STOCK double precision not null,
    BUNDLE_CAPACITY double precision,
    BUNDLE_UNIT_ID bigint,
    primary key (ID)
) type=InnoDB;
create table BASE_ARTICLE_DESCRIPTION (
    ID bigint not null auto_increment,
    DESCRIPTION text not null,
    SYSTEM_LOCALE_KEY varchar(255),
    ARTICLE_ID bigint,
    primary key (ID)
) type=InnoDB;
create table BASE_BANKING (
    ID bigint not null auto_increment,
    BANK_ESTABLISHMENT varchar(255) not null,
    ACCOUNT_NUMBER varchar(255) not null,
    BANK_IDENTIFICATION_NUMBER varchar(255) not null,
    primary key (ID)
) type=InnoDB;
create table BASE_BILL (
    ID bigint not null auto_increment,
    BUSINESS_PARTNER_ID bigint,
    BILL_NUMBER varchar(255) not null,
    BILL_DATE date not null,
    CHARACTERISATION_TYPE varchar(255),
    PREFIX_FREE_TEXT text,
    SUFFIX_FREE_TEXT text,
    primary key (ID)
) type=InnoDB;
create table BASE_BUSINESS_PARTNER (
    ID bigint not null auto_increment,
    CUSTOMER_NUMBER varchar(255) not null unique,
    COMPANY_TITLE varchar(255),
    COMPANY_NAME varchar(255),
    COMPANY_BRANCH varchar(255),
    FOR_ATTENTION_OF bit not null,
    PERSON_ID bigint,
    ADDRESS_ID bigint,
    BANKING_ID bigint,
    primary key (ID)
) type=InnoDB;
create table BASE_DELIVERY_ORDER (
    ID bigint not null auto_increment,
    BUSINESS_PARTNER_ID bigint,
    DELIVERY_ORDER_NUMBER varchar(255) not null unique,
    DELIVERY_ORDER_DATE date not null,
    CHARACTERISATION_TYPE varchar(255) not null,
    PREFIX_FREE_TEXT text,
    SUFFIX_FREE_TEXT text,
    PREPARED_BILL bit not null,
    BILL_ID bigint,
    primary key (ID)
) type=InnoDB;
create table BASE_NUMBER_SEQUENCE (
    ID bigint not null auto_increment,
    SEQUENCE_KEY varchar(255) not null,
    NUMBER varchar(255) not null,
    primary key (ID)
) type=InnoDB;
create table BASE_PERSON (
    ID bigint not null auto_increment,
    TITLE_ID bigint,
    ACADEMIC_TITLE_ID bigint,
    FIRSTNAME varchar(255),
    LASTNAME varchar(255),
    PHONE varchar(255),
    FAX varchar(255),
    EMAIL varchar(255),
    ADDRESS_ID bigint,
    primary key (ID)
) type=InnoDB;
create table BASE_REDUPLICATED_ARTICLE (
    ID bigint not null auto_increment,
    DELIVERY_ORDER_ID bigint,
    ORDER_POSITION integer not null,
    ARTICLE_NUMBER varchar(255) not null,
    QUANTITY double precision not null,
    UNIT varchar(255) not null,
    PRICE double precision not null,
    DESCRIPTION text not null,
    primary key (ID)
) type=InnoDB;
create table BASE_SYSTEM_COUNTRY (
    ID bigint not null auto_increment,
    SYSTEM_COUNTRY_KEY varchar(255) not null unique,
    SYSTEM_LANGUAGE_KEY varchar(255),
    primary key (ID)
) type=InnoDB;
create table BASE_SYSTEM_COUNTY (
    ID bigint not null auto_increment,
    SYSTEM_COUNTY_KEY varchar(255) not null unique,
    SYSTEM_COUNTRY_KEY varchar(255),
    primary key (ID)
) type=InnoDB;
create table BASE_SYSTEM_LANGUAGE (
    ID bigint not null auto_increment,
    SYSTEM_LANGUAGE_KEY varchar(255) not null unique,
    primary key (ID)
) type=InnoDB;
create table BASE_SYSTEM_LOCALE (
    ID bigint not null auto_increment,
    SYSTEM_LOCALE_KEY varchar(255) not null unique,
    SYSTEM_LANGUAGE_KEY varchar(255) not null,
    SYSTEM_COUNTRY_KEY varchar(255) not null,
    VARIANT varchar(255),
    primary key (ID)
) type=InnoDB;
create table BASE_SYSTEM_MESSAGE (
    ID bigint not null auto_increment,
    ADDRESSER varchar(255) not null,
    SUBJECT varchar(255) not null,
    MESSAGE varchar(255) not null,
    primary key (ID)
) type=InnoDB;
create table BASE_SYSTEM_UNIT (
    ID bigint not null auto_increment,
    UNIT_KEY varchar(255) not null,
    CATEGORY varchar(255) not null,
    primary key (ID)
) type=InnoDB;
alter table BASE_ARTICLE 
    add index FK7FD39D68E5FDBA65 (BUNDLE_UNIT_ID), 
    add constraint FK7FD39D68E5FDBA65 
    foreign key (BUNDLE_UNIT_ID) 
    references BASE_SYSTEM_UNIT (ID);
alter table BASE_ARTICLE 
    add index FK7FD39D68DAFE8522 (UNIT_ID), 
    add constraint FK7FD39D68DAFE8522 
    foreign key (UNIT_ID) 
    references BASE_SYSTEM_UNIT (ID);
alter table BASE_ARTICLE_DESCRIPTION 
    add index FK90003785FBC7C981 (ARTICLE_ID), 
    add constraint FK90003785FBC7C981 
    foreign key (ARTICLE_ID) 
    references BASE_ARTICLE (ID);
alter table BASE_BILL 
    add index FK3B8E70B592A54740 (BUSINESS_PARTNER_ID), 
    add constraint FK3B8E70B592A54740 
    foreign key (BUSINESS_PARTNER_ID) 
    references BASE_BUSINESS_PARTNER (ID);
alter table BASE_BUSINESS_PARTNER 
    add index FK2BB6BED7B6A22533 (PERSON_ID), 
    add constraint FK2BB6BED7B6A22533 
    foreign key (PERSON_ID) 
    references BASE_PERSON (ID);
alter table BASE_BUSINESS_PARTNER 
    add index FK2BB6BED733F5BB81 (BANKING_ID), 
    add constraint FK2BB6BED733F5BB81 
    foreign key (BANKING_ID) 
    references BASE_BANKING (ID);
alter table BASE_BUSINESS_PARTNER 
    add index FK2BB6BED7292080C1 (ADDRESS_ID), 
    add constraint FK2BB6BED7292080C1 
    foreign key (ADDRESS_ID) 
    references BASE_ADDRESS (ID);
alter table BASE_DELIVERY_ORDER 
    add index FK98DB0A5192A54740 (BUSINESS_PARTNER_ID), 
    add constraint FK98DB0A5192A54740 
    foreign key (BUSINESS_PARTNER_ID) 
    references BASE_BUSINESS_PARTNER (ID);
alter table BASE_DELIVERY_ORDER 
    add index FK98DB0A51CBBF2233 (BILL_ID), 
    add constraint FK98DB0A51CBBF2233 
    foreign key (BILL_ID) 
    references BASE_BILL (ID);
alter table BASE_PERSON 
    add index FKA96370837F16C806 (ACADEMIC_TITLE_ID), 
    add constraint FKA96370837F16C806 
    foreign key (ACADEMIC_TITLE_ID) 
    references BASE_SYSTEM_UNIT (ID);
alter table BASE_PERSON 
    add index FKA96370837C9C9BCE (TITLE_ID), 
    add constraint FKA96370837C9C9BCE 
    foreign key (TITLE_ID) 
    references BASE_SYSTEM_UNIT (ID);
alter table BASE_PERSON 
    add index FKA9637083292080C1 (ADDRESS_ID), 
    add constraint FKA9637083292080C1 
    foreign key (ADDRESS_ID) 
    references BASE_ADDRESS (ID);
alter table BASE_REDUPLICATED_ARTICLE 
    add index FK41BAB371A0761D98 (DELIVERY_ORDER_ID), 
    add constraint FK41BAB371A0761D98 
    foreign key (DELIVERY_ORDER_ID) 
    references BASE_DELIVERY_ORDER (ID);
