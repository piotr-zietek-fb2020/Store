drop database if exists fb2020;
create database fb2020;
use fb2020;


create table address (
	id int not null auto_increment,
    city varchar(30),
    street varchar(30),
    zip_code varchar(10),
    country varchar(30),
    primary key(id)
);

create table client (
	id int not null auto_increment,
    name varchar(30),
    last_name varchar(30),
    address_id int,
    email varchar(50),
    phone varchar(20),
    foreign key(address_id) references Address(id),
    primary key(id)
);

create table product (
	id int not null auto_increment,
    name varchar(50),
    description varchar(200),
    price decimal(10,2),
    primary key(id)
);

create table `order` (
	id int not null auto_increment,
    total_cost decimal(10,2),
    order_date date,
    client_id int,
    foreign key(client_id) references Client(id),
    primary key(id)
);

create table order_details (
	id int not null auto_increment,
    order_id int,
    product_id int,
    quantity int,
    foreign key(order_id) references `Order`(id),
    foreign key(product_id) references Product(id),
    primary key(id)
);

create table invoice (
	id int not null auto_increment,
    issue_date date,
    buyer_id int,
    order_id int,
    total decimal(10,2),
    foreign key(buyer_id) references Client(id),
    foreign key(order_id) references `Order`(id),
    primary key(id)
);

create table invoice_details (
	id int not null auto_increment,
    invoice_id int,
    product_name varchar(50),
    quantity int,
    amount decimal(10,2),
    foreign key(invoice_id) references Invoice(id),
    primary key(id)
);

insert into address(city, street, zip_code, country)
	values('Lublin', 'Zana', '11-111', 'Polska'),
		  ('Warszawa', 'Mickiewicza', '55-666', 'Polska'),
          ('Kraków', 'SŁowackiego', '22-333', 'Polska'),
		  ('Warszawa', 'Rymwida', '54-321', 'Polska'),
          ('Lublin', 'Wallenroda', '12-345', 'Polska');

insert into client(name, last_name, email, phone, address_id)
	values('Jan', 'Nowak', 'nowak@gmail.com', '123456789', 1),
		  ('Stanisław', 'Dudek', 'dudek@gmail.com', '987654321', 2),
		  ('Franciszek', 'Mazur', 'mazur@gmail.com', '567123980', 3),
		  ('Karolina', 'Szymczak', 'szymczak@gmail.com', '123890465', 4),
		  ('Beata', 'Wójcik', 'wojcik@gmail.com', '555777222', 5);

insert into product(name, description, price)
	values('Wiertarka', 'Wiertarka, która przewierci każdą ściane', 123.45),
		  ('Młotek', 'Młotek, która przebije każdą ściane', 100.00),
		  ('Wiadro', 'Wiadro pojemne jak wanna', 30.00),
		  ('Obcęgi', 'Obcęgi, które....', 55.99),
		  ('Płytki', 'Płytki, które....', 250.00),
		  ('Klej', 'Klej, który....', 13.00),
		  ('Silikon', 'Silikon, który....', 11.50),
		  ('Klucz', 'Klucz, który....', 25.00),
		  ('Deski', 'Deski, które....', 209.00),
		  ('Szlifierka', 'Szlifierka, która....', 300.00),
		  ('Zestaw narzędzi', 'Zestaw narzędzi, który....', 99.99),
		  ('Śrubokręt', 'Śrubokręt, który....', 8.00),
		  ('Skrzynka', 'Skrzynka, któr....', 79.00);
    
insert into `order`(total_cost, order_date, client_id)
	values(436.90, '2020-06-15', 1),
		  (331.99, '2020-02-15', 1),
		  (159.50, '2020-01-15', 1),
		  (718.99, '2020-05-15', 1),
		  (1364.10, '2020-01-15', 2),
		  (655.00, '2020-06-15', 3),
		  (64.00, '2010-06-15', 2);



insert into order_details(order_id, product_id, quantity)
	values(1, 1, 2),
		  (1, 2, 1),
          (1, 3, 3),
          (2, 4, 1),
          (2, 5, 1),
          (2, 6, 2),
          (3, 7, 3),
          (3, 8, 5),
          (4, 9, 2),
          (4, 10, 1),
          (5, 11, 5),
          (5, 1, 7),
          (6, 7, 2),
          (6, 13, 8),
          (7, 12, 8);

insert into invoice(issue_date, buyer_id, order_id, total)
	values('2020-06-15', 1, 1, 436.90),
		  ('2020-02-15', 1, 2, 331.99),
		  ('2020-01-15', 1, 3, 159.50),
		  ('2020-05-15', 1, 4, 718.99),
		  ('2020-01-15', 2, 5, 1364.10),
		  ('2020-06-15', 3, 6, 655),
		  ('2010-06-15', 2, 7, 64);

insert into invoice_details(invoice_id, product_name, quantity, amount)
	values(1, 'Wiertarka', 2, 246.90),
		  (1, 'Młotek', 1, 100),
          (1, 'Wiadro', 3, 90),
          (2, 'Obcęgi', 1, 55.99),
          (2, 'Płytki', 1, 250),
          (2, 'Klej', 2, 26),
          (3, 'Silikon', 3, 80.5),
          (3, 'Klucz', 5, 125),
          (4, 'Deski', 2, 418),
          (4, 'Szlifierka', 1, 300),
          (5, 'Zestaw narzędzi', 5, 499.95),
          (5, 'Wiertarka', 7, 864.15),
          (6, 'Silikon', 2, 23),
          (6, 'Skrzynka', 8, 632),
          (7, 'Srubokręt', 8, 64);


create user if not exists 'fb2020'@'%' identified by 'fb2020';
grant all privileges on fb2020.* to 'fb2020'@'%';
