create table author_embedded
(
    first_name varchar(255),
    last_name varchar(255),
    country varchar(255),
    primary key (first_name, last_name)
) engine = InnoDB;