create table if not exists users
(
    id serial primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    password varchar(255) not null
);

create table if not exists authorities
(
    id serial primary key,
    authority varchar(50) not null,
    user_id bigint not null,
    constraint fk_user foreign key(user_id) references users(id)
);

create table if not exists categories
(
    id serial primary key,
    category varchar(50) not null
);

create table if not exists news
(
    id serial primary key,
    news_content text not null,
    category_id bigint not null,
    user_id bigint not null,
    constraint fk_category foreign key(category_id) references categories(id),
    constraint fk_user foreign key(user_id) references users(id)
);

create table if not exists comments
(
    id serial primary key,
    comment text not null,
    user_id bigint not null,
    news_id bigint not null,
    constraint fk_user foreign key(user_id) references users(id),
    constraint fk_news foreign key(news_id) references news(id)
);

insert into users (first_name, last_name, email, password)
values ('John', 'Connor', 'john@gmail.com', '$2a$12$qo2IFhdP7V/HafVZn2dOWuC9nM3lBRCwBFRIHODMusVx6O/tp/l5u'),
        ('Nick', 'Richardson', 'nick@gmail.com', '$2a$12$klUEwi0.8lQ/0cTOdTSgBO.Vb9n9b2W4u2txDvmGzTN3vrFOkmqAy'),
        ('Bill', 'Billington', 'bill@gmail.com', '$2a$12$wYNDU6MpteFJOsNxiV5HbO3SFPXI605k0Cum.d2GF5xEfKYg60Aw2'),
        ('Frank', 'Sinatra', 'frank@gmail.com', '$2a$12$tsWxli1uFVkBIrZzX/Uh.OXW8bApVlqTpbmpRhN2kl/XooQmDF3eW'),
        ('Alex', 'Romanov', 'alex@gmail.com', '$2a$12$uFo9gU7MAikjzwLKiui5ZuFE45zM4.kNwukGv7joulZVZq9u9ThRi'),
        ('Alfred', 'Morgenstein', 'alfred@gmail.com', '$2a$12$vTZx3wXbSNNupKNkFzosreZdj0yxzWXb.pK9chhNIhqMBsXUtMDDO'),
        ('Brain', 'Panayota', 'brain@gmail.com', '$2a$12$Spu52Tb6JRiL0iwvsZ1hLOYh7TMcCBj.N4Ymq2yg0RRGOHVKB2byu');

insert into authorities (authority, user_id)
values ('ROLE_ADMIN', 1),
        ('ROLE_MODERATOR', 2),
        ('ROLE_USER', 2),
        ('ROLE_USER', 3),
        ('ROLE_USER', 4),
        ('ROLE_USER', 5),
        ('ROLE_MODERATOR', 5),
        ('ROLE_USER', 6),
        ('ROLE_USER', 7);

insert into categories (category)
values ('Internet'),
        ('Culture'),
        ('Religion'),
        ('Science');

insert into news (news_content, category_id, user_id)
values ('The announcement comes after a tumultuous year for Alibaba in 2023, when the company carried out its largest-ever corporate structure overhaul. It also separately implemented several high-profile management changes, with company veteran Eddie Wu taking over the reins as chief executive in September.', 1, 1),
        ('The company earned the right to secure the game by hitting certain viewership metrics this past season as part of its “Thursday Night Football” agreement with the league, the source said. In 2021, Amazon agreed to pay about $1 billion a year for the exclusive rights to Thursday Night Football.', 1, 1),
        ('SoftBank posted its biggest gain in nearly three years at the flagship tech investment arm, the Vision Fund, in the December quarter amid a recovery in valuation of technology companies.', 1, 1),
        ('Eight years after self-doubt, anxiety, alcohol and grief caused her to unravel, the Olivier-winner is now playing an actress trapped in a similar nightmare. She explains how she is taking back control of her ordeal', 2, 2),
        ('The American singer-songwriter answers your questions about working with Johnny Cash and the Rolling Stones, what makes her happy – and why she used to hate All I Wanna Do', 2, 2),
        ('Reesa Teesa, who has amassed 2.3 million followers on the app, spoke to NBC News about her 50-part viral personal series — and what’s next for her.', 2, 2),
        ('Tom Parker has frequently cited Christian theology in rulings, is a supporter of the Seven Mountains Mandate and was a close ally of Roy Moore.', 3, 3),
        ('Some honeybees in Italy regularly steal pollen off the backs of bumblebees', 4, 4),
        ('Lauren Schroeder has loved dinosaurs since age 3 and bones since she was 10. In her second year of university, she started studying the early evolution of the Homo genus and it turned into her Ph.D. Many fossils have taken her breath away, she says, but a 2-million-year-old Homo habilis skull holds such a special place in her heart that it’s tattooed on her forearm.', 4, 5),
        ('Astronomers are puzzled over an enigmatic companion to a pulsar', 4, 6);

insert into comments (comment, news_id, user_id)
values ('Wow, this is very interesting', 1, 7),
        ('I have never been interested in football.', 2, 5),
        ('Cool!', 3, 3),
        ('I do not know who it is.', 4, 4),
        ('I would ask a couple of questions.', 5, 1),
        ('Okay, it is interesting', 6, 2),
        ('Oh, I did not know', 7, 7),
        ('I love bees and their honey', 8, 6),
        ('Dinosaurs are interesting', 9, 5),
        ('Space is full of mysteries', 10, 3),
        ('It is very interesting', 10, 4);
