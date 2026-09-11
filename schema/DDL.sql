use Kosta_kiosk;

show tables;

CREATE TABLE `user` (
    user_id     INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(20) NOT NULL,
    phone       VARCHAR(11) NOT NULL,
    stamp       INT NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id),
    UNIQUE KEY phone_number (phone)
);

CREATE TABLE coupon (
    coupon_id   INT NOT NULL AUTO_INCREMENT,
    user_id     INT NOT NULL,
    price       INT NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (coupon_id),
    FOREIGN KEY (user_id) REFERENCES `user`(user_id)
);

create table manager(
	admin_id int primary key auto_increment not null,
    name varchar(20) not null,
    id varchar(20) unique key not null,
    password varchar(255) not null
);

create table category(
	category_id int primary key auto_increment not null,
    category_name varchar(10) not null
);

create table menu(
	menu_id int primary key auto_increment not null,
    category_id int not null,
    menu_name varchar(15) not null,
    description varchar(255) not null,
    price int not null,
    hot_ice enum('HOT','ICE') not null,
    created_at datetime not null default current_timestamp,
    soldout boolean not null default false,
    
    foreign key(category_id) references category(category_id) on delete restrict
);

create table `order`(
	order_id int primary key auto_increment not null,
    user_id int not null,
    sum int not null,
    created_at datetime not null default current_timestamp,
    
    foreign key (user_id) references `user`(user_id)
);

create table order_detail(
	detail_id int primary key auto_increment not null,
    order_id int not null,
    menu_id int not null,
    amount int not null,
    size enum('SMALL','MEDIUM','LARGE') not null default 'MEDIUM',
    shot int not null default 2,
    ice enum('FEW','LOT','DEFAULT') null,
    syrup int not null default 0,
    
    foreign key (order_id) references `order`(order_id),
    foreign key (menu_id) references menu(menu_id)
);

alter table coupon alter column price set default 2000;
alter table `order` modify column user_id int null;
alter table manager rename column admin_id to manager_id;