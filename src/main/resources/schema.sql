create table if not exists account (
    active bit not null,
    id varchar(36) not null,
    name varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists benefit (
    balance decimal(38,2),
    id varchar(36) not null,
    account_id varchar(255),
    type enum ('FOOD','MEAL'),
    created_at datetime(6),
    updated_at datetime(6),
    primary key (id)
) engine=InnoDB;

create table if not exists merchants (
    id varchar(36) not null,
    mcc varchar(255),
    name varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists transaction (
    total_amount decimal(38,2),
    created_at datetime(6),
    updated_at datetime(6),
    id varchar(36) not null,
    account_id varchar(255),
    mcc varchar(255),
    merchant_name varchar(255),
    response_code enum ('AUTHORIZED','INTERNAL_ERROR','REJECTED'),
    status enum ('COMPLETED','FAILED','PROCESSING','STARTED'),
    primary key (id)
) engine=InnoDB;

create table if not exists wallet (
    id varchar(36) not null,
    balance decimal(38,2),
    account_id varchar(36) not null,
    created_at datetime(6),
    updated_at datetime(6),
    primary key (account_id)
) engine=InnoDB;