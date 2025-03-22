package com.marciocesar.walletserviceassignment.core.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BALANCE_LOG") // Map to the balance_log table
public class BalanceLogEntity {

    @EmbeddedId
    private BalanceLogId id;

    @Column(name = "REVTYPE", nullable = false)
    private Integer revisionType; // 0 = Add, 1 = Update, 2 = Delete

    @Column(name = "WALLET_ID", nullable = false)
    private Long walletId;

    @Getter
    @Column(name = "AMOUNT", precision = 10, scale = 2)
    private BigDecimal amount;

    @Getter
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class BalanceLogId implements Serializable {
        private Long id;      // Primary key of BalanceEntity
        private Integer rev;  // Revision ID
    }
}
