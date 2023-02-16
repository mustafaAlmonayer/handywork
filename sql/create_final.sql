use handywork;

drop table if exists jobs;
drop table if exists users;
drop table if exists image_url;
drop table if exists job_sequence;
drop table if exists user_sequence;

CREATE TABLE image_url (
    job_id BIGINT NOT NULL,
    images_urls VARCHAR(255)
)  ENGINE=INNODB;
CREATE TABLE job_sequence (
    next_val BIGINT
)  ENGINE=INNODB;
insert into job_sequence values ( 1 );
CREATE TABLE jobs (
    id BIGINT NOT NULL,
    description TEXT NOT NULL,
    field VARCHAR(50) NOT NULL,
    job_name VARCHAR(50) NOT NULL,
    price INTEGER CHECK (price >= 0 AND price <= 999999),
    publish_date DATETIME NOT NULL,
    user_id BIGINT,
    PRIMARY KEY (id)
)  ENGINE=INNODB;
CREATE TABLE user_sequence (
    next_val BIGINT
)  ENGINE=INNODB;
insert into user_sequence values ( 1 );
CREATE TABLE users (
    id BIGINT NOT NULL,
    authority VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(36),
    last_name VARCHAR(36),
    password VARCHAR(36) NOT NULL,
    phone_number VARCHAR(10),
    profile_picture VARCHAR(255),
    user_name VARCHAR(36) NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB;
alter table users add constraint unique_user_name unique (user_name);
alter table users add constraint unique_email unique (email);
alter table users add constraint unique_phone_number unique (phone_number);
alter table image_url add constraint job_image_url_foreign_key foreign key (job_id) references jobs (id);
alter table jobs add constraint job_user_foreign_key foreign key (user_id) references users (id);
