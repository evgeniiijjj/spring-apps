create table if not exists categories
(
    id serial primary key,
    title varchar(50) not null
);

create table if not exists books
(
    id serial primary key,
    title varchar(250) not null,
    author varchar(250) not null,
    categories_id int not null,
    constraint books_categories_fk foreign key(categories_id) references categories(id)
);

insert into categories(title)
values ('NOVEL'),
        ('SCI_FI'),
        ('ADVENTURE'),
        ('FANTASY');

insert into books(title, author, categories_id)
values ('Старик и море', 'Хемингуэй', 1),
        ('Гиперболоид инженера Гарина', 'А. Толстой', 2),
        ('Ревизор', 'Н. Гоголь', 1),
        ('Солярис', 'С. Лем', 2),
        ('Час быка', 'И. Ефремов', 2)