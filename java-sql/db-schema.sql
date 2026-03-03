

create database mts_db;
use mts_db;

create table accounts(
    account_number varchar(20) primary key,
    account_holder_name varchar(100) not null,
    balance decimal(15, 2) not null,
    account_type varchar(20) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);


insert into accounts (account_number, account_holder_name, balance, account_type) values
                                                                                      ('1234567890', 'John Doe', 1000.00, 'savings'),
                                                                                      ('0987654321', 'Jane Smith', 2000.00, 'checking');

create table transactions(
    transaction_id int auto_increment primary key,
    account_number varchar(20) not null,
    amount decimal(15, 2) not null,
    transaction_type varchar(20) not null,
    transaction_date timestamp default current_timestamp,
    description varchar(255),
    foreign key (account_number) references accounts(account_number)
);
