package com.marciocesar.walletserviceassignment.core.database.entities;

import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FINANCIAL_MOVEMENT")
public class FinancialMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_EXTERNAL_CODE", nullable = false, updatable = false)
    private UUID customerExternalCode;

    @Column(name = "WALLET_EXTERNAL_CODE", nullable = false, updatable = false)
    private UUID walletExternalCode;

    @Column(name = "THIRD_CUSTOMER_EXTERNAL_CODE", updatable = false)
    private UUID thirdCustomerExternalCode;

    @Column(name = "THIRD_WALLET_EXTERNAL_CODE", updatable = false)
    private UUID thirdWalletExternalCode;

    @Column(name = "AMOUNT", nullable = false, precision = 10, scale = 2, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, updatable = false)
    private TypeFinancialMovementEnum type;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", updatable = false, nullable = false)
    private LocalDateTime creationDate;
}
