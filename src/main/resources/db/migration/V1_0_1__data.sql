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
