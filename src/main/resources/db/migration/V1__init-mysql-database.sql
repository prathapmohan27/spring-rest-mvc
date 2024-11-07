
    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        beer_style tinyint not null check (beer_style between 0 and 9),
        price decimal(38,2) not null,
        quantity_on_hand integer,
        version integer,
        created_on datetime(6),
        updated_on datetime(6),
        id varchar(36) not null,
        beer_name varchar(50) not null,
        brand varchar(255),
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        creation_date datetime(6),
        last_update_date datetime(6),
        id varchar(36) not null,
        customer_name varchar(255),
        primary key (id)
    ) engine=InnoDB;
