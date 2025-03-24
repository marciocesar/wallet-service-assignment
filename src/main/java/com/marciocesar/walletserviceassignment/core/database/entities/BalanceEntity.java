package com.marciocesar.walletserviceassignment.core.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@AuditTable(value = "BALANCE_LOG")
@Table(name = "BALANCE")
public class BalanceEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JoinColumn(name = "WALLET_ID", nullable = false, unique = true, updatable = false)
    private WalletEntity wallet;

    @Column(name = "AMOUNT", nullable = false, scale = 2)
    private BigDecimal amount;

    @NotAudited
    @CreationTimestamp
    @Column(name = "CREATION_DATE", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;
}
