create database AL_Motamad_Auto;
use AL_Motamad_Auto;
show databases;

CREATE TABLE IF NOT EXISTS USER (
USER_ID INT NOT NULL auto_increment,
USERNAME VARCHAR(30) NOT NULL,
PASSWORD VARCHAR(30) NOT NULL,
EMAIL VARCHAR(30) NOT NULL,
PRIMARY KEY(USER_ID)
);
select * from USER;

create table customer (
    cid int primary key,
    cname varchar (30),
    caddress varchar (20),
    cage int,
    cLicenceID int
);

ALTER TABLE customer ADD cphone VARCHAR(15);
ALTER TABLE customer MODIFY cid int auto_increment;
ALTER TABLE customer auto_increment=1;


create table warehouse (
    wid int primary key,
    wname varchar (30),
    wlocation varchar (20),
    wcapacity int,
    wavailableCars int
);
ALTER TABLE warehouse MODIFY wavailableCars int DEFAULT 0;
ALTER TABLE warehouse MODIFY wid int auto_increment;
ALTER TABLE warehouse auto_increment=1;

create table supplier (
    sid int primary key,
    sname varchar (30),
    sphoneNum varchar (15),
    scountry varchar (20),
    srating int
);
ALTER TABLE supplier MODIFY COLUMN srating REAL;

ALTER TABLE supplier MODIFY sid int auto_increment;
ALTER TABLE supplier auto_increment=1;

create table car (
    cid int primary key,
    sid int,
    wid int,
    cmake varchar (20),
    cmodel varchar (20),
    cyear int,
    cColor varchar (10),
    cPrice real,

    foreign key (sid) references supplier (sid) 
        on delete cascade,

    foreign key (wid) references warehouse (wid)
        on delete cascade
);
ALTER TABLE car MODIFY cid int auto_increment;
ALTER TABLE car auto_increment=1;

show tables;


SELECT *
FROM USER;
-- Create Employee table
CREATE TABLE employee (
    Eid INT PRIMARY KEY auto_increment,
    name VARCHAR(32),
    address VARCHAR(32),
    salary INT
);
ALTER TABLE employee AUTO_INCREMENT = 1;
create table Employeephone (
 Eid int,
 phoneNumber varchar (16),
 primary key (Eid,phoneNumber),
 foreign key (Eid) references employee(Eid) on delete cascade
);

create table Transaction(
	Transaction_Id int primary key auto_increment,
    Customer_Id int,
    Car_Id int,
    emp_id int,
    Date date,
    Pay_Type varchar(30),
    Paid_Amount double,
    price double,
    Discount double,
    foreign key (Customer_Id) references customer (cid) 
        on delete cascade,
	foreign key (Car_Id) references car (cid) on delete cascade,
    foreign key (emp_id) references employee(Eid) 
    on delete cascade on update cascade
);
ALTER TABLE Transaction AUTO_INCREMENT = 1;

ALTER TABLE `transaction` DROP FOREIGN KEY `transaction_ibfk_3`;
ALTER TABLE `transaction` 
ADD CONSTRAINT `transaction_ibfk_3` 
FOREIGN KEY (`emp_id`) 
REFERENCES `employee` (`Eid`) 
ON DELETE CASCADE;


-- increment available cars in warehouse when adding a new car
DELIMITER //
CREATE TRIGGER after_car_insert
AFTER INSERT ON car
FOR EACH ROW
BEGIN
    UPDATE warehouse
    SET wavailableCars = wavailableCars + 1
    WHERE wid = NEW.wid;
END;
//
DELIMITER ;

-- decrement available cars in warehouse when deleting a new car
DELIMITER //
CREATE TRIGGER after_car_delete
AFTER DELETE ON car
FOR EACH ROW
BEGIN
    UPDATE warehouse
    SET wavailableCars = wavailableCars - 1
    WHERE wid = OLD.wid;
END;
//
DELIMITER ;

-- decrement available cars in warehouse when adding the car to transaction
DELIMITER //
CREATE TRIGGER after_transaction_insert
AFTER INSERT ON transaction
FOR EACH ROW
BEGIN
    UPDATE warehouse
    SET wavailableCars = wavailableCars - 1
    WHERE wid = (SELECT wid FROM car WHERE cid = NEW.Car_Id);
END;
//
DELIMITER ;


show  create table  Transaction;

select * from employee;
select * from car;
select * from customer;
select * from warehouse;
select * from supplier;
select * from Transaction;


INSERT INTO customer (cname, caddress, cage, cLicenceID, cphone) 
VALUES 
('Alice Johnson', '123 Maple Ave', 30, 101010, '555-1234'),
('Michael Green', '456 Pine St', 45, 202020, '555-5678'),
('Emily Davis', '789 Cedar Rd', 25, 303030, '555-9012');

INSERT INTO supplier (sname, sphoneNum, scountry, srating) 
VALUES 
('Supplier A', '1234567890', 'USA', 4.5),
('Supplier B', '0987654321', 'Germany', 4.8),
('Supplier C', '1122334455', 'Japan', 4.2);


INSERT INTO warehouse (wname, wlocation, wcapacity) 
VALUES 
('Warehouse 1', 'New York', 50),
('Warehouse 2', 'Berlin', 100),
('Warehouse 3', 'Tokyo', 70);


INSERT INTO employee (name, address, salary) 
VALUES 
('John Doe', '123 Main St', 50000),
('Jane Smith', '456 Elm St', 60000),
('Bob Brown', '789 Oak St', 55000);


INSERT INTO car (sid, wid, cmake, cmodel, cyear, cColor, cPrice) 
VALUES 
-- 4 BMW cars
(1, 1, 'BMW', 'X5', 2023, 'Black', 60000),
(1, 1, 'BMW', 'X3', 2022, 'White', 50000),
(2, 2, 'BMW', '330i', 2021, 'Blue', 55000),
(2, 2, 'BMW', 'M4', 2024, 'Red', 70000),
-- 3 Mercedes cars
(3, 3, 'Mercedes', 'C-Class', 2023, 'Black', 65000),
(3, 3, 'Mercedes', 'E-Class', 2022, 'White', 75000),
(1, 1, 'Mercedes', 'GLA', 2021, 'Silver', 55000),
-- 3 other types of cars
(2, 2, 'Toyota', 'Camry', 2020, 'Gray', 30000),
(3, 3, 'Audi', 'A4', 2023, 'Blue', 45000),
(1, 1, 'Ford', 'Mustang', 2022, 'Yellow', 55000);


INSERT INTO `transaction` (Customer_Id, Car_Id, emp_id, Date, Pay_Type, Paid_Amount, price, Discount) 
VALUES 
(1, 1, 1, '2025-01-01', 'Credit Card', 60000, 60000, 0),
(1, 2, 2, '2025-01-02', 'Cash', 50000, 50000, 0),
(2, 3, 3, '2025-01-03', 'Bank Transfer', 55000, 55000, 0),
(2, 4, 1, '2025-01-04', 'Credit Card', 70000, 70000, 0),
(3, 5, 2, '2025-01-05', 'Cash', 65000, 65000, 0);

-- Adding 10 new cars to the `car` table
INSERT INTO car (sid, wid, cmake, cmodel, cyear, cColor, cPrice)
VALUES
(1, 1, 'Honda', 'Civic', 2023, 'Red', 30000),
(2, 2, 'Toyota', 'Corolla', 2023, 'Blue', 25000),
(3, 3, 'Mazda', 'CX-5', 2023, 'White', 35000),
(1, 1, 'Ford', 'F-150', 2023, 'Black', 45000),
(2, 2, 'Chevrolet', 'Malibu', 2023, 'Silver', 27000),
(3, 3, 'Tesla', 'Model 3', 2023, 'Blue', 55000),
(1, 1, 'Hyundai', 'Elantra', 2023, 'Gray', 22000),
(2, 2, 'Kia', 'Sorento', 2023, 'White', 33000),
(3, 3, 'Jeep', 'Wrangler', 2023, 'Black', 40000),
(1, 1, 'Volkswagen', 'Passat', 2023, 'Silver', 28000);

-- Adding transactions for the newly added cars
INSERT INTO `transaction` (Customer_Id, Car_Id, emp_id, Date, Pay_Type, Paid_Amount, price, Discount)
VALUES
(1, 11, 1, '2023-01-10', 'Credit Card', 30000, 30000, 0),
(2, 12, 2, '2023-02-15', 'Cash', 25000, 25000, 0),
(3, 13, 3, '2023-03-20', 'Bank Transfer', 35000, 35000, 0),
(1, 14, 1, '2023-04-25', 'Credit Card', 45000, 45000, 0),
(2, 15, 2, '2023-05-30', 'Cash', 27000, 27000, 0),
(3, 16, 3, '2023-06-10', 'Bank Transfer', 55000, 55000, 0),
(1, 17, 1, '2023-07-15', 'Credit Card', 22000, 22000, 0),
(2, 18, 2, '2023-08-20', 'Cash', 33000, 33000, 0),
(3, 19, 3, '2023-09-25', 'Bank Transfer', 40000, 40000, 0),
(1, 20, 1, '2023-10-30', 'Credit Card', 28000, 28000, 0);








                    