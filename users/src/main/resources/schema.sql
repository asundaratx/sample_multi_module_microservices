
create table users
(
   id integer,
   firstname varchar(255) not null,
   lastname varchar(255) not null,
   email varchar(255) not null,
   userid varchar(255) not null,
   encryptedpassword varchar(255) not null,
   primary key(id)
);