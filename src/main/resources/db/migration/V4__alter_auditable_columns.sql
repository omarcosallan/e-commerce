-- DROP COLUMNS
ALTER TABLE tb_users
    DROP COLUMN created_by,
    DROP COLUMN created_date,
    DROP COLUMN last_modified_by,
    DROP COLUMN last_modified_date;

ALTER TABLE tb_products
    DROP COLUMN created_by,
    DROP COLUMN created_date,
    DROP COLUMN last_modified_by,
    DROP COLUMN last_modified_date;

ALTER TABLE tb_orders
    DROP COLUMN created_by,
    DROP COLUMN created_date,
    DROP COLUMN last_modified_by,
    DROP COLUMN last_modified_date;

ALTER TABLE tb_payments
    DROP COLUMN created_by,
    DROP COLUMN created_date,
    DROP COLUMN last_modified_by,
    DROP COLUMN last_modified_date;

-- ADD COLUMNS
ALTER TABLE tb_users
    ADD COLUMN created_by INT,
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by  INT,
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE tb_users
    ADD CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES tb_users (id),
    ADD CONSTRAINT fk_users_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES tb_users (id);

ALTER TABLE tb_products
    ADD COLUMN created_by INT,
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by  INT,
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE tb_products
    ADD CONSTRAINT fk_products_created_by FOREIGN KEY (created_by) REFERENCES tb_users (id),
    ADD CONSTRAINT fk_products_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES tb_users (id);

ALTER TABLE tb_orders
    ADD COLUMN created_by INT,
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by   INT,
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE tb_orders
    ADD CONSTRAINT fk_orders_created_by FOREIGN KEY (created_by) REFERENCES tb_users (id),
    ADD CONSTRAINT fk_orders_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES tb_users (id);

ALTER TABLE tb_payments
    ADD COLUMN created_by INT,
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by   INT,
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE tb_payments
    ADD CONSTRAINT fk_payments_created_by FOREIGN KEY (created_by) REFERENCES tb_users (id),
    ADD CONSTRAINT fk_payments_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES tb_users (id);