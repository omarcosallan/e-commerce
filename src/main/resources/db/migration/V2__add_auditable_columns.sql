ALTER TABLE tb_users
    ADD COLUMN created_by VARCHAR(50),
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by   VARCHAR(50),
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tb_products
    ADD COLUMN created_by VARCHAR(50),
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by   VARCHAR(50),
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tb_orders
    ADD COLUMN created_by VARCHAR(50),
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by   VARCHAR(50),
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tb_payments
    ADD COLUMN created_by VARCHAR(50),
    ADD COLUMN created_date       TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN last_modified_by   VARCHAR(50),
    ADD COLUMN last_modified_date TIMESTAMP WITHOUT TIME ZONE;