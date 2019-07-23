--------------------------------------------------------------------------------------
-- Name	       : OT (Oracle Tutorial) Sample Database
-- Link	       : http://www.oracletutorial.com/oracle-sample-database/
-- Version     : 1.0
-- Last Updated: July-28-2017
-- Copyright   : Copyright � 2017 by www.oracletutorial.com. All Rights Reserved.
-- Notice      : Use this sample database for the educational purpose only.
--               Credit the site oracletutorial.com explitly in your materials that
--               use this sample database.
--------------------------------------------------------------------------------------
DROP TABLE playground.order_items;
DROP TABLE playground.orders;
DROP TABLE playground.inventories;
DROP TABLE playground.products;
DROP TABLE playground.product_categories;
DROP TABLE playground.warehouses;
DROP TABLE playground.employees;
DROP TABLE playground.contacts;
DROP TABLE playground.customers;
DROP TABLE playground.locations;
DROP TABLE playground.countries;
DROP TABLE playground.regions;

drop sequence regions_seq;
drop sequence countries_seq;
drop sequence locations_seq;
drop sequence customers_seq;
drop sequence contacts_seq;
drop sequence employees_seq;
drop sequence warehouses_seq;
drop sequence products_seq;
drop sequence product_categories_seq;
drop sequence orders_seq;