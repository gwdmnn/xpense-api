CREATE TABLE CATEGORY
(
    id   SERIAL NOT NULL,
    name VARCHAR(36),
    CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE CATEGORY
    ADD CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name);
---------------
CREATE TABLE OUTPUT
(
    id          SERIAL           NOT NULL,
    amount      DOUBLE PRECISION NOT NULL,
    description VARCHAR(255),
    date        date             NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    category_id BIGINT,
    CONSTRAINT pk_output PRIMARY KEY (id)
);

ALTER TABLE OUTPUT
    ADD CONSTRAINT FK_OUTPUT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);
-----------------

CREATE TABLE INCOME
(
    id          SERIAL           NOT NULL,
    amount      DOUBLE PRECISION NOT NULL,
    description VARCHAR(255),
    date        date             NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_income PRIMARY KEY (id)
);

-----------------

--mocked income data
INSERT INTO INCOME (amount, date, description) VALUES
(5000.00, '2023-01-15', 'test'),
(6000.00, '2023-02-20', 'test'),
(7000.00, '2023-03-10', 'test'),
(8000.00, '2023-04-05', 'test'),
(9000.00, '2023-05-25', 'test');

--mocked category data
INSERT INTO CATEGORY (name) VALUES
('Food'),
('Transport'),
('Entertainment'),
('Health'),
('Education');

--mocked output data
INSERT INTO OUTPUT (amount, date, category_id, description) VALUES
(2000.00, '2023-01-20',1, 'test'),
(2500.00, '2023-02-25',2, 'test'),
(3000.00, '2023-03-15',3, 'test'),
(3500.00, '2023-04-10',4, 'test'),
(4000.00, '2023-05-30',5, 'test');
