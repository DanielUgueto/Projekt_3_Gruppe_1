DROP database IF EXISTS bilabonnement;
CREATE DATABASE IF NOT EXISTS bilabonnement;

USE bilabonnement;

CREATE TABLE zip_code
(
    zip_code VARCHAR(255) PRIMARY KEY,
    city     VARCHAR(255) NOT NULL,
    country  VARCHAR(255) NOT NULL
);

CREATE TABLE customer
(
    customer_id            INT PRIMARY KEY AUTO_INCREMENT,
    first_name             VARCHAR(255) NOT NULL,
    last_name              VARCHAR(255) NOT NULL,
    drivers_license_number INT          NOT NULL UNIQUE,
    cpr_number             VARCHAR(20)  NOT NULL UNIQUE,
    email                  VARCHAR(255) NOT NULL,
    phone_number           INT
);

CREATE TABLE customer_address
(
    customer_address_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id         INT          NOT NULL,
    zip_code            VARCHAR(255) NOT NULL,
    street_name         VARCHAR(255) NOT NULL,
    house_number        VARCHAR(255) NOT NULL,
    floor               VARCHAR(255),
    CONSTRAINT fk_customer_address_customer
        FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT fk_customer_address_zip_code
        FOREIGN KEY (zip_code) REFERENCES zip_code (zip_code)
);

CREATE TABLE car_brand
(
    car_brand_id INT PRIMARY KEY AUTO_INCREMENT,
    brand_name   VARCHAR(255) NOT NULL
);

CREATE TABLE car_model
(
    car_model_id    INT PRIMARY KEY AUTO_INCREMENT,
    car_brand_id    INT          NOT NULL,
    model_name      VARCHAR(255) NOT NULL,
    equipment_level VARCHAR(255) NOT NULL,
    shift_gear_type VARCHAR(255) NOT NULL,
    fuel_type       VARCHAR(255) NOT NULL,
    CONSTRAINT fk_car_model_brand
        FOREIGN KEY (car_brand_id) REFERENCES car_brand (car_brand_id)
);

CREATE TABLE car
(
    car_id              INT PRIMARY KEY AUTO_INCREMENT,
    car_model_id        INT            NOT NULL,
    vin_number          VARCHAR(17)    NOT NULL UNIQUE,
    license_plate       VARCHAR(7)     NOT NULL UNIQUE,
    monthly_price       DECIMAL(19, 2) NOT NULL,
    status              VARCHAR(255)   NOT NULL,
    colour              VARCHAR(255)   NOT NULL,
    registration_date   VARCHAR(255)   NOT NULL,
    CONSTRAINT fk_car_model
        FOREIGN KEY (car_model_id) REFERENCES car_model (car_model_id)
);

CREATE TABLE employee
(
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    work_email  VARCHAR(255) NOT NULL UNIQUE,
    role        VARCHAR(255) NOT NULL
);

CREATE TABLE rental_contract
(
    rental_contract_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id        INT          NOT NULL,
    customer_id        INT          NOT NULL,
    car_id             INT          NOT NULL,
    start_date         DATE         NOT NULL,
    end_date           DATE         NOT NULL,
    pickup_location    VARCHAR(255) NOT NULL,
    status             VARCHAR(255) NOT NULL,
    subscription_type  VARCHAR(255) NOT NULL,
    CONSTRAINT fk_rental_contract_employee
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
    CONSTRAINT fk_rental_contract_customer
        FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT fk_rental_contract_car
        FOREIGN KEY (car_id) REFERENCES car (car_id)
);

CREATE TABLE damage_report
(
    damage_report_id   INT PRIMARY KEY AUTO_INCREMENT,
    rental_contract_id INT            NOT NULL,
    employee_id        INT            NOT NULL,
    created_at         DATE           NOT NULL,
    total_price        DECIMAL(10, 2) NOT NULL,
    description        VARCHAR(255)   NOT NULL,
    CONSTRAINT fk_damage_report_rental_contract
        FOREIGN KEY (rental_contract_id) REFERENCES rental_contract (rental_contract_id),
    CONSTRAINT fk_damage_report_employee
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
);

CREATE TABLE damage_category
(
    damage_category_id INT PRIMARY KEY AUTO_INCREMENT,
    name               VARCHAR(255)   NOT NULL,
    standard_price     DECIMAL(10, 2) NOT NULL,
    description        VARCHAR(255)
);

CREATE TABLE damage
(
    damage_id          INT PRIMARY KEY AUTO_INCREMENT,
    damage_report_id   INT NOT NULL,
    damage_category_id INT NOT NULL,
    CONSTRAINT fk_damage_report
        FOREIGN KEY (damage_report_id) REFERENCES damage_report (damage_report_id),
    CONSTRAINT fk_damage_category
        FOREIGN KEY (damage_category_id) REFERENCES damage_category (damage_category_id)
);
