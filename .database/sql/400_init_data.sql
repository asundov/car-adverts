INSERT INTO core.car_advert (id, title, fuel_type, price, is_new, mileage, first_registration, active, created_by, created_date, modified_by, modified_date)
VALUES
    (1, '2018 Toyota Corolla', 'Petrol', 15000, FALSE, 45000, '2018-03-15', 1, 101, '2024-08-24 10:00:00', 101, '2024-08-24 10:00:00'),
    (2, '2020 Tesla Model 3', 'Electric', 35000, TRUE, 1000, '2020-09-01', 1, 102, '2024-08-24 11:00:00', 102, '2024-08-24 11:00:00'),
    (3, '2017 Ford Focus', 'Diesel', 12000, FALSE, 60000, '2017-07-20', 1, 103, '2024-08-24 12:00:00', 103, '2024-08-24 12:00:00'),
    (4, '2022 BMW X5', 'Hybrid', 55000, TRUE, 5000, '2022-05-05', 1, 104, '2024-08-24 13:00:00', 104, '2024-08-24 13:00:00'),
    (5, '2019 Audi A4', 'Petrol', 30000, FALSE, 30000, '2019-11-11', 1, 105, '2024-08-24 14:00:00', 105, '2024-08-24 14:00:00');

INSERT INTO conf."user" (id, first_name, last_name, username, admin, status, active)
VALUES
(1, 'John', 'Doe', 'johndoe', false, 1, 1),
(2, 'Jane', 'Smith', 'janesmith', false, 1, 1),
(3, 'Alice', 'Johnson', 'alicejohnson', false, 1, 1),
(4, 'Bob', 'Brown', 'bobbrown', false, 1, 1),
(5, 'Charlie', 'Davis', 'charliedavis', false, 1, 1);

INSERT INTO conf.user_function (id, ucode, uname, status, active)
VALUES
(1, 'UF01', 'admin', 1, 1),
(2, 'UF02', 'sluzbenik', 1, 1),
(3, 'UF03', 'korisnik', 1, 1);

INSERT INTO conf.user_x_function (id, user_id, function_id, active)
VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 3, 1, 1),
(4, 1, 2, 1),
(5, 1, 3, 1);

INSERT INTO conf.user_x_role (id, user_id, role_id, active)
VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 3, 1, 1),
(4, 1, 2, 1),
(5, 1, 3, 1);

INSERT INTO conf.user_role_x_function (id, role_id, function_id, active)
VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 3, 1, 1),
(4, 1, 2, 1),
(5, 1, 3, 1);

INSERT INTO conf.user_role (id, ucode, uname, active)
VALUES
(1, 'UR01', 'admin', 1),
(2, 'UR02', 'sluzbenik', 1),
(3, 'UR03', 'korisnik', 1);
