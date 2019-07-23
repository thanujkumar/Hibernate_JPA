--------------------------------------------------------------------------------------
-- Name	       : OT (Oracle Tutorial) Sample Database
-- Link	       : http://www.oracletutorial.com/oracle-sample-database/
-- Version     : 1.0
-- Last Updated: July-28-2017
-- Copyright   : Copyright 2017 by www.oracletutorial.com. All Rights Reserved.
-- Notice      : Use this sample database for the educational purpose only.
--               Credit the site oracletutorial.com explitly in your materials that
--               use this sample database.
--------------------------------------------------------------------------------------
-- Below scripts are modified
---------------------------------------------------------------------------
-- execute the following statements to create tables
---------------------------------------------------------------------------
-- regions
CREATE TABLE regions
  (
    region_id NUMBER PRIMARY KEY,
    region_name VARCHAR2( 50 ) NOT NULL,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL
  );
-- countries table
CREATE TABLE countries
  (
    country_id   CHAR( 2 ) PRIMARY KEY  ,
    country_name VARCHAR2( 40 ) NOT NULL,
    region_id    NUMBER                 , -- fk
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_countries_regions FOREIGN KEY( region_id )
      REFERENCES regions( region_id ) 
      ON DELETE CASCADE
  );

-- location
CREATE TABLE locations
  (
    location_id NUMBER PRIMARY KEY      ,
    address     VARCHAR2( 255 ) NOT NULL,
    postal_code VARCHAR2( 20 )          ,
    city        VARCHAR2( 50 )          ,
    state       VARCHAR2( 50 )          ,
    country_id  CHAR( 2 )               , -- fk
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_locations_countries 
      FOREIGN KEY( country_id )
      REFERENCES countries( country_id ) 
      ON DELETE CASCADE
  );
-- warehouses
CREATE TABLE warehouses
  (
    warehouse_id NUMBER PRIMARY KEY,
    warehouse_name VARCHAR( 255 ) ,
    location_id    NUMBER( 12, 0 ), -- fk
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_warehouses_locations 
      FOREIGN KEY( location_id )
      REFERENCES locations( location_id ) 
      ON DELETE CASCADE
  );
-- employees
CREATE TABLE employees
  (
    employee_id NUMBER  PRIMARY KEY,
    first_name VARCHAR( 255 ) NOT NULL,
    last_name  VARCHAR( 255 ) NOT NULL,
    email      VARCHAR( 255 ) NOT NULL,
    phone      VARCHAR( 50 ) NOT NULL ,
    hire_date  DATE NOT NULL          ,
    manager_id NUMBER( 12, 0 )        , -- fk
    job_title  VARCHAR( 255 ) NOT NULL,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_employees_manager 
        FOREIGN KEY( manager_id )
        REFERENCES employees( employee_id )
        ON DELETE CASCADE
  );
-- product category
CREATE TABLE product_categories
  (
    category_id NUMBER PRIMARY KEY,
    category_name VARCHAR2( 255 ) NOT NULL,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL
  );

-- products table
CREATE TABLE products
  (
    product_id NUMBER  PRIMARY KEY,
    product_name  VARCHAR2( 255 ) NOT NULL,
    description   VARCHAR2( 2000 )        ,
    standard_cost NUMBER( 9, 2 )          ,
    list_price    NUMBER( 9, 2 )          ,
    category_id   NUMBER NOT NULL         ,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_products_categories 
      FOREIGN KEY( category_id )
      REFERENCES product_categories( category_id ) 
      ON DELETE CASCADE
  );
-- customers
CREATE TABLE customers
  (
    customer_id NUMBER  PRIMARY KEY,
    name         VARCHAR2( 255 ) NOT NULL,
    address      VARCHAR2( 255 )         ,
    website      VARCHAR2( 255 )         ,
    credit_limit NUMBER( 8, 2 ),
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL
  );
-- contacts
CREATE TABLE contacts
  (
    contact_id NUMBER  PRIMARY KEY,
    first_name  VARCHAR2( 255 ) NOT NULL,
    last_name   VARCHAR2( 255 ) NOT NULL,
    email       VARCHAR2( 255 ) NOT NULL,
    phone       VARCHAR2( 20 )          ,
    customer_id NUMBER                  ,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_contacts_customers 
      FOREIGN KEY( customer_id )
      REFERENCES customers( customer_id ) 
      ON DELETE CASCADE
  );
-- orders table
CREATE TABLE orders
  (
    order_id NUMBER PRIMARY KEY,
    customer_id NUMBER( 6, 0 ) NOT NULL, -- fk
    status      VARCHAR( 20 ) NOT NULL ,
    salesman_id NUMBER( 6, 0 )         , -- fk
    order_date  DATE NOT NULL          ,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT fk_orders_customers 
      FOREIGN KEY( customer_id )
      REFERENCES customers( customer_id )
      ON DELETE CASCADE,
    CONSTRAINT fk_orders_employees 
      FOREIGN KEY( salesman_id )
      REFERENCES employees( employee_id ) 
      ON DELETE SET NULL
  );
-- order items
CREATE TABLE order_items
  (
    order_id   NUMBER( 12, 0 )                                , -- fk
    item_id    NUMBER( 12, 0 )                                ,
    product_id NUMBER( 12, 0 ) NOT NULL                       , -- fk
    quantity   NUMBER( 8, 2 ) NOT NULL                        ,
    unit_price NUMBER( 8, 2 ) NOT NULL                        ,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT pk_order_items 
      PRIMARY KEY( order_id, item_id ),
    CONSTRAINT fk_order_items_products 
      FOREIGN KEY( product_id )
      REFERENCES products( product_id ) 
      ON DELETE CASCADE,
    CONSTRAINT fk_order_items_orders 
      FOREIGN KEY( order_id )
      REFERENCES orders( order_id ) 
      ON DELETE CASCADE
  );
-- inventories
CREATE TABLE inventories
  (
    product_id   NUMBER( 12, 0 )        , -- fk
    warehouse_id NUMBER( 12, 0 )        , -- fk
    quantity     NUMBER( 8, 0 ) NOT NULL,
    CREATED_BY VARCHAR2(16 CHAR) NOT NULL, 
    CREATED_TS TIMESTAMP (6) NOT NULL, 
    MODIFIED_BY VARCHAR2(16 CHAR), 
    MODIFIED_TS TIMESTAMP (6), 
    VERSION NUMBER(18,0) NOT NULL,
    CONSTRAINT pk_inventories 
      PRIMARY KEY( product_id, warehouse_id ),
    CONSTRAINT fk_inventories_products 
      FOREIGN KEY( product_id )
      REFERENCES products( product_id ) 
      ON DELETE CASCADE,
    CONSTRAINT fk_inventories_warehouses 
      FOREIGN KEY( warehouse_id )
      REFERENCES warehouses( warehouse_id ) 
      ON DELETE CASCADE
  );

  -- Sequences for each of the table
  CREATE SEQUENCE REGIONS_SEQ
  MINVALUE 1
  START WITH 7
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

  select REGIONS_SEQ.nextval from dual;

  CREATE SEQUENCE COUNTRIES_SEQ
  MINVALUE 1
  START WITH 26
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

  select COUNTRIES_SEQ.nextval from dual;

  CREATE SEQUENCE LOCATIONS_SEQ
  MINVALUE 1
  START WITH 24
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

  select LOCATIONS_SEQ.nextval from dual;

  CREATE SEQUENCE CONTACTS_SEQ
  MINVALUE 1
  START WITH 320
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

  select CONTACTS_SEQ.nextval from dual;

  CREATE SEQUENCE CUSTOMERS_SEQ
  MINVALUE 1
  START WITH 320
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

 select CUSTOMERS_SEQ.nextval from dual;

  CREATE SEQUENCE EMPLOYEES_SEQ
  MINVALUE 1
  START WITH 108
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

 select EMPLOYEES_SEQ.nextval from dual;

  CREATE SEQUENCE ORDERS_SEQ
  MINVALUE 1
  START WITH 106
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

 select ORDERS_SEQ.nextval from dual;

  CREATE SEQUENCE PRODUCT_CATEGORIES_SEQ
  MINVALUE 1
  START WITH 6
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

 select PRODUCT_CATEGORIES_SEQ.nextval from dual;

  CREATE SEQUENCE PRODUCTS_SEQ
  MINVALUE 1
  START WITH 289
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

 select PRODUCTS_SEQ.nextval from dual;

  CREATE SEQUENCE WAREHOUSES_SEQ
  MINVALUE 1
  START WITH 10
  INCREMENT BY 50 -- Matching JPA default cache
  CACHE 50;

 select WAREHOUSES_SEQ.nextval from dual;