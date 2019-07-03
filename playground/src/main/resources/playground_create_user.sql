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
--------------------------------------------------------------------
-- execute the following statements to create a user name OT and
-- grant priviledges
--------------------------------------------------------------------

-- create new user
CREATE USER PLAYGROUND IDENTIFIED BY PLAYGROUND default tablespace APP_DATA temporary tablespace temp quota unlimited on APP_DATA;

-- grant priviledges
GRANT FORCE ANY TRANSACTION, CREATE SESSION, create table, create view, create sequence, create procedure, create type, create trigger, create synonym to PLAYGROUND;
GRANT EXECUTE ON SYS.DBMS_LOCK to PLAYGROUND;

--ALTER SESSION SET CURRENT_SCHEMA=OTDATA;
--select sys_context('userenv', 'current_schema') from dual;

--GRANT CONNECT, RESOURCE, DBA TO PLAYGROUND;