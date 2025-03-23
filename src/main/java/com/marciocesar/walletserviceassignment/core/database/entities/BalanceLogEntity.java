package com.marciocesar.walletserviceassignment.core.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BALANCE_LOG")
public class BalanceLogEntity {

    @EmbeddedId
    private BalanceLogId id;

    @Column(name = "REVTYPE", nullable = false)
    private Integer revisionType;

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
        private Long id;
        private Integer rev;
    }
}
