create table post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(400) NOT NULL,
    content VARCHAR(2000) NOT NULL,
    created timestamp
);

create table comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    content VARCHAR(2000) NOT NULL,
    created timestamp
);

create table role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    role_name VARCHAR(2000) NOT NULL
);

create table user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(2000) NOT NULL,
    password VARCHAR(2000) NOT NULL
);

alter table comment
    ADD CONSTRAINT comment_post_id
    FOREIGN KEY (post_id) REFERENCES post(id);

alter table role
    ADD CONSTRAINT user_id
    FOREIGN KEY (user_id) REFERENCES user(id)


