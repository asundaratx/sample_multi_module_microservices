create table accounts
(
   id integer,
   userid varchar(255) not null,
   accountid varchar(255) not null,
   balance double not null,
   primary key(id)
);
create sequence account_seq start with 100 increment by 50;