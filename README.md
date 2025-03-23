### If I had more time, I would like to implement the following improvements and evolutions:

- Time spent on the project was limited, so I focused on the main requirements.

| Title                          | Description                                                                                                                                                                                                                                                                                                                                                                    |
|--------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Kafka (topic)**              | This application is responsible for wallets, so it does not handle customer information. Customer information would come from a topic fed by another application.                                                                                                                                                                                                              |
| **Customer Validation**        | Security validations on the customer to perform transactions and create wallets.                                                                                                                                                                                                                                                                                               |
| **RabbitMQ (queues)**          | The financial transaction endpoints are synchronous. Considering a context of thousands of requests, the ideal is to separate this flow into specific queues, making the process asynchronous.                                                                                                                                                                                 |
| **Multimodules (api, events)** | The application was created in a single module to facilitate and simplify development. However, applying the creation of the Kafka consumer and the creation of queues for processing financial transactions, the ideal is to separate it into 2 modules sharing the core, with both modules running in separate containers. The database would remain the same, being shared. |

### Technologies Used

| Technology            | Description                                                                                                   |
|-----------------------|---------------------------------------------------------------------------------------------------------------|
| **Java 21**           | The primary programming language used for developing the application.                                         |
| **Spring Boot 3.1.4** | A framework that simplifies the development of Java applications by providing a comprehensive infrastructure. |
| **Gradle 8.13**       | The build automation tool used for managing project dependencies and building the application.                |

### Running the Application with Docker Compose

To run the application using Docker Compose, follow these steps:

1. Ensure you have Docker and Docker Compose installed on your machine.
2. The latest version of the code already has a .jar file in the `/jar` directory of the project for easy execution. It is
   not necessary to generate a new .jar . If you want to generate a new .jar, just run the command and move the generated
   .jar to the `/jar` directory of the project.
    ```bash
    ./gradlew clean build
   mv build/libs/wallet-service-assignment-0.0.1-SNAPSHOT.jar jar/
    ```

3. Run the following command to start the application:
    ```bash
    docker-compose up
    ```

This will start the application and make it accessible at `http://localhost:8081`.

### API Documentation

#### BalanceSearchController

1. **Search Balance**
    - **Endpoint:** `GET /wallets/{walletExternalCode}/balances`
    - **Description:** Retrieves the balance for a specific wallet.
    - **Parameters:**
        - `walletExternalCode` (UUID): The external code of the wallet.
    - **Response:** `BalanceResponse` object containing the balance details.
    - **Example Request:**
      ```bash
      curl -X GET "http://localhost:8081/wallets/{walletExternalCode}/balances"
      ```

2. **Search Balance Per Period**
    - **Endpoint:** `GET /wallets/{walletExternalCode}/balances/statement`
    - **Description:** Retrieves the balance statement for a specific wallet within a given period.
    - **Parameters:**
        - `walletExternalCode` (UUID): The external code of the wallet.
        - `startDate` (LocalDate): The start date of the period (format: `yyyy-MM-dd`).
        - `endDate` (LocalDate): The end date of the period (format: `yyyy-MM-dd`).
        - `page` (int, optional): The page number (default: 0).
        - `size` (int, optional): The page size (default: 10).
    - **Response:** `Page<DailyWalletSummaryResponse>` object containing the balance summary for each day within the
      period.
    - **Example Request:**
      ```bash
      curl -X GET "http://localhost:8081/wallets/{walletExternalCode}/balances/statement?startDate=2025-10-01&endDate=2025-10-31&page=0&size=10"
      ```
    - **Note:** There is already pre-registered data that can be used to retrieve daily balances. Use the following
      `walletExternalCode`: `2d3d8d84-02ab-460b-b3d4-1d90c0f109ae`. The balance data for the period between `2025-10-01`
      and `2025-10-09` can be found in the file `/resource/data.sql`.

#### FinancialMovementController

1. **Deposit**
    - **Endpoint:** `PUT /wallets/balances/deposit`
    - **Description:** Deposits an amount into a wallet.
    - **Request Body:** `DepositBalanceRequest` object containing the deposit details.
    - **Response:** `BalanceResponse` object containing the updated balance details.
    - **Example Request:**
      ```bash
      curl -X PUT "http://localhost:8081/wallets/balances/deposit" -H "Content-Type: application/json" -d '{"amount": 100.0, "walletExternalCode": "{walletExternalCode}"}'
      ```

2. **Withdraw**
    - **Endpoint:** `PUT /wallets/balances/withdraw`
    - **Description:** Withdraws an amount from a wallet.
    - **Request Body:** `WithdrawalBalanceRequest` object containing the withdrawal details.
    - **Response:** `BalanceResponse` object containing the updated balance details.
    - **Example Request:**
      ```bash
      curl -X PUT "http://localhost:8081/wallets/balances/withdraw" -H "Content-Type: application/json" -d '{"amount": 50.0, "walletExternalCode": "{walletExternalCode}"}'
      ```

3. **Transfer**
    - **Endpoint:** `PUT /wallets/balances/transfer`
    - **Description:** Transfers an amount from one wallet to another.
    - **Request Body:** `TransferBalanceRequest` object containing the transfer details.
    - **Response:** `BalanceResponse` object containing the updated balance details.
    - **Example Request:**
      ```bash
      curl -X PUT "http://localhost:8081/wallets/balances/transfer" -H "Content-Type: application/json" -d '{"amount": 30.0, "sourceWalletExternalCode": "{sourceWalletExternalCode}", "targetWalletExternalCode": "{targetWalletExternalCode}"}'
      ```

#### WalletController

1. **Create Wallet**
    - **Endpoint:** `POST /wallets`
    - **Description:** Creates a new wallet.
    - **Request Body:** `CreateWalletRequest` object containing the wallet creation details.
    - **Response:** `CreateWalletResponse` object containing the created wallet details.
    - **Example Request:**
      ```bash
      curl -X POST "http://localhost:8081/wallets" -H "Content-Type: application/json" -d '{"name": "My Wallet", "currency": "USD"}'
      ```