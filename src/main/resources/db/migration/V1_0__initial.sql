create table colors
(
    id   uuid default random_uuid() not null primary key,
    name text                       not null
);

create table sizes
(
    id   uuid default random_uuid() not null primary key,
    name text                       not null
);

create table products
(
    id   uuid default random_uuid() not null primary key,
    name text                       not null
);

create table product_variants
(
    id         uuid default random_uuid() not null primary key,
    product_id uuid                       not null references products,
    color_id   uuid references colors,
    size_id    uuid references sizes
);