create table post(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(400) NOT NULL,
    content VARCHAR(2000) NULL,
    created timestamp
);

create table comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    content VARCHAR(2000) NULL,
    created timestamp
);

alter table comment
    ADD CONSTRAINT comment_post_id
    FOREIGN KEY (post_id) REFERENCES post(id)