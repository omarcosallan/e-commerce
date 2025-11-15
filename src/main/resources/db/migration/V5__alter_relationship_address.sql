ALTER TABLE tb_users
    DROP COLUMN address_id;

ALTER TABLE tb_address
    ADD COLUMN user_id INT;

ALTER TABLE tb_address
    ADD CONSTRAINT fk_users_address_id FOREIGN KEY (user_id) REFERENCES tb_users (id);