package com.marciocesar.walletserviceassignment.core.database.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "WALLET")
public class WalletEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "EXTERNAL_CODE", nullable = false, unique = true, updatable = false)
    private UUID walletExternalCode = UUID.randomUUID();

    @CreationTimestamp
    @Column(name = "CREATION_DATE", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "ID_CUSTOMER", updatable = false, nullable = false, unique = true)
    private CustomerEntity customer;

    @Setter
    @OneToOne(mappedBy = "wallet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BalanceEntity balance;
}
