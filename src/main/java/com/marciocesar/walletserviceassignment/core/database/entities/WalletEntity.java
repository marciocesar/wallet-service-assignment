package com.marciocesar.walletserviceassignment.core.database.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "WALLET")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "ID_CUSTOMER", nullable = false, unique = true)
    private CustomerEntity customer;

    @Setter
    @OneToOne(mappedBy = "wallet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BalanceEntity balance;
}
