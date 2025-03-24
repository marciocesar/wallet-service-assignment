### Features and improvements that can be made

- Time spent on the project was limited, so I focused on the main requirements.

| Title                                                         | Description                                                                                                                                                                                                                                                                                                                                                                   |
|---------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Kafka (topic)**                                             | This application is responsible for wallets, so it does not handle customer information. Customer information would come from a topic fed by another application.                                                                                                                                                                                                             |
| **Customer Validation**                                       | Security validations on the customer to perform transactions and create wallets.                                                                                                                                                                                                                                                                                              |
| **RabbitMQ (queues)**                                         | The financial transaction endpoints are synchronous. Considering a context of thousands of requests, the ideal is to separate this flow into specific queues, making the process asynchronous.                                                                                                                                                                                |
| **Multimodules (api, events)**                                | The application was created in a single module to facilitate and simplify development. However, applying the creation of the Kafka consumer and the creation of queues for processing financial transactions, the ideal is to separate it into 2 modules sharing the core, with both modules running in separate containers. The database would remain the same, being shared. |
| **Configure Access to Swagger in Docker Compose and Docker** | Configure Docker to access Swagger from the container. At the moment, Swagger can only be accessed when running outside the container.                                                                                                                                                                                                                                              |

### Technologies Used

| Technology            | Description                                                                                                   |
|-----------------------|---------------------------------------------------------------------------------------------------------------|
| **Java 21**           | The primary programming language used for developing the application.                                         |
| **Spring Boot 3.1.4** | A framework that simplifies the development of Java applications by providing a comprehensive infrastructure. |
| **Gradle 8.13**       | The build automation tool used for managing project dependencies and building the application.                |

### Architecture and Design Pattern 
The base pattern used in the project implementation is "client->domain->persistence":
- Controller Layer:
  - Handles HTTP implementation and input validation
  - Orchestrates REST responses
- Service Layer:
  - Contains all core business logic
  - Ensures the Single Responsibility Principle
- In the implementation of financial movements: Deposit, Withdraw, and Transfer, the **Strategy Pattern** can be observed:
  - Ensures that the specific logic of each implementation is separated and reused.
  - Guarantees extensibility
  - Ensures the Open/Closed Principle (OCP)
  - Keeps implementations cohesive and with a single responsibility

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

The file `requests.postman_collection.json` was added to the root of the project with all the requests that can be imported into Postman.

#### WalletController

1. **Create Wallet**
   - **Endpoint:** `POST /wallets`
   - **Description:** Creates a new wallet.
   - **Request Body:** `CreateWalletRequest` object containing the wallet creation details.
   - **Response:** `CreateWalletResponse` object containing the created wallet details.
   - **List of Valid:** `customerExternalCode` that can be used to generate wallets are
     ```
      2d3d8d84-02ab-460b-b3d4-1d90c0f109ae,
      1bde4228-0c26-4f3d-8172-7735bf45d299,
      b281ba01-6d6b-4689-9166-406e21fef589,
      146f8e47-6da2-48f3-a446-4e2d8b30a718,
      d0e79ab8-e8e6-4899-89f0-20c57c08ad99,
      4ea1657f-8064-4a68-982e-84d498a48e47,
      3dc52ad4-6c81-4c4d-b9d4-8f6591bc1ec0,
      1e8eb393-f17b-4ab0-9840-53651d3ffc4f,
      28c99b75-3640-4705-b776-81aff634f8bc,
      48750f12-1bd5-424d-9b34-8ad73a67c928
     ```
   - **Example Request:**
     ```bash
     curl --location --request POST 'http://localhost:8081/wallets' \
     --header 'Content-Type: application/json' \
     --data '{"customerExternalCode": "1bde4228-0c26-4f3d-8172-7735bf45d299"}'
     ```

#### BalanceSearchController

1. **Search Balance**
    - **Endpoint:** `GET /wallets/{walletExternalCode}/balances`
    - **Description:** Retrieves the balance for a specific wallet.
    - **Parameters:**
        - `walletExternalCode` (UUID): The external code of the wallet.
    - **Response:** `BalanceResponse` object containing the balance details.
    - **Example Request:**
      ```bash
      curl --location --request GET 'http://localhost:8081/wallets/889cb550-e68e-4a5d-b73d-e0a5d608124a/balances' \
      --header 'Content-Type: application/json' \
      --data ''
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
    - **Response:** `Page<DailyWalletSummaryResponse>` object containing the balance summary for each day within the period.
    - **Example Request:**
      ```bash
      curl --location --request GET 'http://localhost:8081/wallets/889cb550-e68e-4a5d-b73d-e0a5d608124a/balances/statement?startDate=2025-01-09&endDate=2025-10-09&size=15' \
      --header 'Content-Type: application/json' \
      --data ''
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
      curl --location --request PUT 'http://localhost:8081/wallets/balances/deposit' \
       --header 'Content-Type: application/json' \
       --data '{"customerExternalCode": "2d3d8d84-02ab-460b-b3d4-1d90c0f109ae", "walletExternalCode": "889cb550-e68e-4a5d-b73d-e0a5d608124a","amount": "10"}'
      ```

2. **Withdraw**
    - **Endpoint:** `PUT /wallets/balances/withdraw`
    - **Description:** Withdraws an amount from a wallet.
    - **Request Body:** `WithdrawalBalanceRequest` object containing the withdrawal details.
    - **Response:** `BalanceResponse` object containing the updated balance details.
    - **Example Request:**
      ```bash
       curl --location --request PUT 'http://localhost:8081/wallets/balances/withdraw' \
      --header 'Content-Type: application/json' \
      --data '{"customerExternalCode": "2d3d8d84-02ab-460b-b3d4-1d90c0f109ae","walletExternalCode": "889cb550-e68e-4a5d-b73d-e0a5d608124a","amount": "4"}'
      ```

3. **Transfer**
    - **Endpoint:** `PUT /wallets/balances/transfer`
    - **Description:** Transfers an amount from one wallet to another (The two wallets need to be added to the system beforehand).
    - **Request Body:** `TransferBalanceRequest` object containing the transfer details.
    - **Response:** `BalanceResponse` object containing the updated balance details.
    - **Example Request:**
      ```bash
      curl --location --request PUT 'http://localhost:8081/wallets/balances/transfer' \
      --header 'Content-Type: application/json' \
      --data '{"customerExternalCode": "1bde4228-0c26-4f3d-8172-7735bf45d299","walletExternalCode": "889cb550-e68e-4a5d-b73d-e0a5d608124a","thirdCustomerExternalCode": "d0e79ab8-e8e6-4899-89f0-20c57c08ad99","thirdWalletExternalCode": "c954618e-ae4f-4f35-8893-9e96ef0b700a","amount": "6"}'
      ```
