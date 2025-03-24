package com.marciocesar.walletserviceassignment.core.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CUSTOMER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CUSTOMER_EMAIL", columnNames = "EMAIL")
})
public class CustomerEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "EXTERNAL_CODE", nullable = false, updatable = false, unique = true, length = 36)
    private UUID customerExternalCode;

    @Email(message = "Email address must be valid")
    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    private WalletEntity wallet;
}
