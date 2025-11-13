CREATE TABLE tb_address
(
    id         SERIAL       NOT NULL PRIMARY KEY,
    street      VARCHAR(255) NOT NULL,
    number      INT          NOT NULL,
    city        VARCHAR(255) NOT NULL,
    state       VARCHAR(255) NOT NULL,
    complement  VARCHAR(255),
    postal_code VARCHAR(255) NOT NULL
);

CREATE TABLE tb_users
(
    id            SERIAL              NOT NULL PRIMARY KEY,
    username      VARCHAR(50) UNIQUE  NOT NULL,
    email         VARCHAR(120) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    address_id    INT,

    FOREIGN KEY (address_id) REFERENCES tb_address (id)
);

CREATE TABLE tb_categories
(
    id   SERIAL       NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE tb_products
(
    id             SERIAL         NOT NULL PRIMARY KEY,
    name           VARCHAR(100)   NOT NULL,
    description    TEXT,
    price          DECIMAL(10, 2) NOT NULL CHECK (price > 0.0),
    stock_quantity INT            NOT NULL CHECK (stock_quantity > 0),
    category_id    INT,

    FOREIGN KEY (category_id) REFERENCES tb_categories (id)
);

CREATE TABLE tb_cart_items
(
    id         SERIAL NOT NULL PRIMARY KEY,
    user_id    INT    NOT NULL,
    product_id INT    NOT NULL,
    quantity   INT    NOT NULL,

    FOREIGN KEY (user_id) REFERENCES tb_users (id),
    FOREIGN KEY (product_id) REFERENCES tb_products (id)
);

CREATE TABLE tb_orders
(
    id           SERIAL         NOT NULL PRIMARY KEY,
    user_id      INT            NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL CHECK (total_amount > 0.0),
    status       VARCHAR(50) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CANCELED', 'DELIVERED', 'SHIPPED')),
    address_id   INT,

    FOREIGN KEY (user_id) REFERENCES tb_users (id),
    FOREIGN KEY (address_id) REFERENCES tb_address (id)
);

CREATE TABLE tb_order_items
(
    order_id   INT            NOT NULL,
    product_id INT            NOT NULL,
    quantity   INT            NOT NULL CHECK (quantity > 0),
    price      DECIMAL(10, 2) NOT NULL CHECK (price > 0.0),
    sub_total  DECIMAL(10, 2) NOT NULL CHECK (sub_total > 0.0),

    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES tb_orders (id),
    FOREIGN KEY (product_id) REFERENCES tb_products (id)
);

CREATE TABLE tb_payments
(
    id             SERIAL         NOT NULL PRIMARY KEY,
    order_id       INT            NOT NULL,
    payment_method VARCHAR(50),
    total_amount   DECIMAL(10, 2) NOT NULL CHECK (total_amount > 0.0),
    status         VARCHAR(50) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CANCELED', 'PAID', 'REVERSED')),

    FOREIGN KEY (order_id) REFERENCES tb_orders (id)
);
