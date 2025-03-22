-- Insert random customers without wallets
INSERT INTO CUSTOMER (NAME, EXTERNAL_CODE, EMAIL, STATUS, BIRTHDAY, CREATION_DATE)
VALUES
    ('John Doe', '2d3d8d84-02ab-460b-b3d4-1d90c0f109ae', 'johndoe@email.com', 'ACTIVE', '1985-05-20',CURRENT_TIMESTAMP),
    ('Jane Smith', '1bde4228-0c26-4f3d-8172-7735bf45d299', 'janesmith@email.com', 'INACTIVE', '1990-08-12',CURRENT_TIMESTAMP),
    ('Alice Johnson', 'b281ba01-6d6b-4689-9166-406e21fef589', 'alicej@email.com', 'ACTIVE', '1995-03-15',CURRENT_TIMESTAMP),
    ('Robert Brown', '146f8e47-6da2-48f3-a446-4e2d8b30a718', 'robertb@email.com', 'ACTIVE', '1982-12-05',CURRENT_TIMESTAMP),
    ('Emily White', 'd0e79ab8-e8e6-4899-89f0-20c57c08ad99', 'emilyw@email.com', 'INACTIVE', '1979-07-25',CURRENT_TIMESTAMP),
    ('Michael Green', '4ea1657f-8064-4a68-982e-84d498a48e47', 'michaelg@email.com', 'ACTIVE', '1992-11-03',CURRENT_TIMESTAMP),
    ('Sarah Wilson', '3dc52ad4-6c81-4c4d-b9d4-8f6591bc1ec0', 'sarahw@email.com', 'INACTIVE', '1988-01-18',CURRENT_TIMESTAMP),
    ('David Lee', '1e8eb393-f17b-4ab0-9840-53651d3ffc4f', 'davidl@email.com', 'ACTIVE', '1975-09-30',CURRENT_TIMESTAMP),
    ('Sophia Adams', '28c99b75-3640-4705-b776-81aff634f8bc', 'sophiaa@email.com', 'ACTIVE', '2000-06-25',CURRENT_TIMESTAMP),
    ('Chris Brown', '48750f12-1bd5-424d-9b34-8ad73a67c928', 'chrisb@email.com', 'INACTIVE', '1983-10-14',CURRENT_TIMESTAMP);
