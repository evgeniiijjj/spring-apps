drop table if exists bookings, users, rooms, hotels;

create table hotels
(
    id serial primary key,
    hotel_name varchar(50) not null,
    ad_title varchar(50) not null,
    city varchar(50) not null,
    address varchar(255) not null,
    city_center_distance int not null,
    rating real,
    number_of_ratings int
);

create table rooms
(
    id serial primary key,
    room_name varchar(50) not null,
    description varchar(255) not null,
    number int not null,
    price int not null,
    capacity int not null,
    hotel_id bigint not null,
    constraint fk_hotel foreign key(hotel_id) references hotels(id)
);

create table users
(
    id serial primary key,
    user_name varchar(50) not null,
    password varchar(255) not null,
    email varchar(50) not null,
    role varchar(50) not null
);

create table bookings
(
    id serial primary key,
    room_id bigint not null,
    user_id bigint not null,
    check_in timestamp with time zone not null,
    check_out timestamp with time zone not null,
    constraint fk_room foreign key(room_id) references rooms(id),
    constraint fk_user foreign key(user_id) references users(id)
);

insert into hotels (hotel_name, ad_title, city, address, city_center_distance, rating, number_of_ratings)
values ('first_hotel', 'the best hotel', 'Moskow', 'ul.Moskowskay, d.23', 200, 4, 4),
        ('second_hotel', 'the very best hotel', 'Moskow', 'ul.Lenina, d.3', 150, 3, 19),
        ('third_hotel', 'the very very best hotel', 'Moskow', 'ul.Mira, d.30', 750, 4, 39);

insert into rooms (room_name, description, number, price, capacity, hotel_id)
values ('first_room', 'nice room', 1, 633, 2, 1),
        ('second_room', 'very nice room', 2, 890, 2, 1),
        ('third_room', 'very very nice room', 3, 786, 2, 2),
        ('first_room', 'nice room', 1, 1000, 2, 2),
        ('second_room', 'very nice room', 2, 1000, 2, 2),
        ('third_room', 'very very nice room', 3, 1000, 2, 2),
        ('first_room', 'very nice room', 1, 1000, 2, 3),
        ('second_room', 'very nice room', 2, 1000, 2, 3),
        ('third_room', 'very very nice room', 3, 1000, 2, 3);

insert into users (user_name, password, email, role)
values ('John', '$2a$12$qo2IFhdP7V/HafVZn2dOWuC9nM3lBRCwBFRIHODMusVx6O/tp/l5u', 'john@gmail.com', 'ROLE_ADMIN'),
        ('Nick', '$2a$12$klUEwi0.8lQ/0cTOdTSgBO.Vb9n9b2W4u2txDvmGzTN3vrFOkmqAy', 'nick@gmail.com', 'ROLE_USER'),
        ('Bill', '$2a$12$wYNDU6MpteFJOsNxiV5HbO3SFPXI605k0Cum.d2GF5xEfKYg60Aw2', 'bill@gmail.com', 'ROLE_USER'),
        ('Frank', '$2a$12$tsWxli1uFVkBIrZzX/Uh.OXW8bApVlqTpbmpRhN2kl/XooQmDF3eW', 'frank@gmail.com', 'ROLE_USER'),
        ('Alex', '$2a$12$uFo9gU7MAikjzwLKiui5ZuFE45zM4.kNwukGv7joulZVZq9u9ThRi', 'alex@gmail.com', 'ROLE_USER'),
        ('Alfred', '$2a$12$vTZx3wXbSNNupKNkFzosreZdj0yxzWXb.pK9chhNIhqMBsXUtMDDO', 'alfred@gmail.com', 'ROLE_USER'),
        ('Brain', '$2a$12$Spu52Tb6JRiL0iwvsZ1hLOYh7TMcCBj.N4Ymq2yg0RRGOHVKB2byu', 'brain@gmail.com', 'ROLE_USER');

insert into bookings (room_id, user_id, check_in, check_out)
values (1, 7, '2023-04-05 12:00:00+00', '2023-04-15 12:00:00+00'),
        (2, 5, '2024-03-05 12:00:00+00', '2024-04-15 12:00:00+00'),
        (3, 3, '2024-02-05 12:00:00+00', '2024-02-15 12:00:00+00'),
        (4, 4, '2024-03-03 12:00:00+00', '2024-03-23 12:00:00+00'),
        (5, 1, '2024-03-07 12:00:00+00', '2024-04-21 12:00:00+00'),
        (6, 2, '2024-03-01 12:00:00+00', '2024-04-15 12:00:00+00'),
        (7, 7, '2024-02-03 12:00:00+00', '2024-03-15 12:00:00+00'),
        (8, 6, '2024-03-09 12:00:00+00', '2024-04-17 12:00:00+00'),
        (9, 5, '2024-03-05 12:00:00+00', '2024-04-26 12:00:00+00');