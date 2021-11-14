create table book (id bigint generated by default as identity, author varchar(255), description varchar(255), isbn bigint, name varchar(255), price double, type integer, primary key (id))
create table category (id integer generated by default as identity, category varchar(255), discount double, is_enable boolean, primary key (id))
alter table book add constraint FKct95elxsd1u65b65bm6x4jbg2 foreign key (type) references category
