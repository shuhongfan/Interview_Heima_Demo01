drop table if exists account;
create table account
(
    accountNo int primary key auto_increment,
    balance   int not null
);

insert into account (accountNo, balance)
values (1, 1000),
       (2, 1000);