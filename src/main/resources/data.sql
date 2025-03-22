-- Insert random customers without wallets
INSERT INTO CUSTOMER (NAME, EXTERNAL_CODE, EMAIL, STATUS, BIRTHDAY, CREATION_DATE)
VALUES ('John Doe', '2d3d8d84-02ab-460b-b3d4-1d90c0f109ae', 'johndoe@email.com', 'ACTIVE', '1985-05-20',
        CURRENT_TIMESTAMP),
       ('Jane Smith', '1bde4228-0c26-4f3d-8172-7735bf45d299', 'janesmith@email.com', 'INACTIVE', '1990-08-12',
        CURRENT_TIMESTAMP),
       ('Alice Johnson', 'b281ba01-6d6b-4689-9166-406e21fef589', 'alicej@email.com', 'ACTIVE', '1995-03-15',
        CURRENT_TIMESTAMP),
       ('Robert Brown', '146f8e47-6da2-48f3-a446-4e2d8b30a718', 'robertb@email.com', 'ACTIVE', '1982-12-05',
        CURRENT_TIMESTAMP),
       ('Emily White', 'd0e79ab8-e8e6-4899-89f0-20c57c08ad99', 'emilyw@email.com', 'INACTIVE', '1979-07-25',
        CURRENT_TIMESTAMP),
       ('Michael Green', '4ea1657f-8064-4a68-982e-84d498a48e47', 'michaelg@email.com', 'ACTIVE', '1992-11-03',
        CURRENT_TIMESTAMP),
       ('Sarah Wilson', '3dc52ad4-6c81-4c4d-b9d4-8f6591bc1ec0', 'sarahw@email.com', 'INACTIVE', '1988-01-18',
        CURRENT_TIMESTAMP),
       ('David Lee', '1e8eb393-f17b-4ab0-9840-53651d3ffc4f', 'davidl@email.com', 'ACTIVE', '1975-09-30',
        CURRENT_TIMESTAMP),
       ('Sophia Adams', '28c99b75-3640-4705-b776-81aff634f8bc', 'sophiaa@email.com', 'ACTIVE', '2000-06-25',
        CURRENT_TIMESTAMP),
       ('Chris Brown', '48750f12-1bd5-424d-9b34-8ad73a67c928', 'chrisb@email.com', 'INACTIVE', '1983-10-14',
        CURRENT_TIMESTAMP);

-- Insert random wallet with balances
INSERT INTO WALLET (CREATION_DATE, ID_CUSTOMER)
VALUES ('2025-03-01 12:00:00', 1);

-- Inserção de registros na tabela BALANCE
INSERT INTO BALANCE (ID_WALLET, AMOUNT, CREATION_DATE, UPDATE_DATE)
VALUES (1, 100, '2025-10-01 12:00:00', '2025-10-12 12:00:00');

INSERT INTO REVINFO (REV, REVTSTMP)
VALUES (1, 1759310400000),  -- 2025-10-01 12:00:00 em epoch (milissegundos)
       (2, 1759396800000),  -- 2025-10-02 12:00:00
       (3, 1759483200000),  -- 2025-10-03 12:00:00
       (4, 1759569600000),  -- 2025-10-04 12:00:00
       (5, 1759656000000),  -- 2025-10-05 12:00:00
       (6, 1759742400000),  -- 2025-10-06 12:00:00
       (7, 1759828800000),  -- 2025-10-07 12:00:00
       (8, 1759915200000),  -- 2025-10-08 12:00:00
       (9, 1760001600000),  -- 2025-10-09 12:00:00
       (10, 1760088000000), -- 2025-10-10 12:00:00
       (11, 1760174400000), -- 2025-10-11 12:00:00
       (12, 1760260800000); -- 2025-10-12 12:00:00


INSERT INTO BALANCE_LOG (ID, REV, REVTYPE, AMOUNT, UPDATE_DATE, WALLET_ID)
VALUES (1, 1, 0, 0.00, '2025-10-01 12:00:00', 1),    -- Add: Inicializa com 0
       (1, 2, 1, 500.50, '2025-10-02 12:00:00', 1),  -- Update: Alteração para 500.50
       (1, 3, 1, 1500.75, '2025-10-03 12:00:00', 1), -- Update: Alteração para 1500.75
       (1, 4, 1, 1500.75, '2025-10-04 12:00:00', 1), -- Update: Sem alteração
       (1, 5, 1, 1500.75, '2025-10-05 12:00:00', 1), -- Update: Sem alteração
       (1, 6, 1, 1500.75, '2025-10-06 12:00:00', 1), -- Update: Sem alteração
       (1, 7, 1, 1500.75, '2025-10-07 12:00:00', 1), -- Update: Sem alteração
       (1, 8, 1, 1500.75, '2025-10-08 12:00:00', 1), -- Update: Sem alteração
       (1, 10, 1, 299.00, '2025-10-09 12:10:00', 1), -- Update: Alteração para 500
       (1, 11, 1, 100.00, '2025-10-09 12:12:00', 1), -- Update: Alteração para 500
       (1, 12, 1, 155.00, '2025-10-09 12:14:00', 1); -- Update: Alteração para 500

