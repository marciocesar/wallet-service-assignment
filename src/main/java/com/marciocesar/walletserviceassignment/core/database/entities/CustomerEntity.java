package com.marciocesar.walletserviceassignment.core.database.entities;

import com.marciocesar.walletserviceassignment.core.enums.CustomerStatusEnum;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "EXTERNAL_CODE", nullable = false, updatable = false, unique = true, length = 36)
    private UUID externalCustomerCode;

    @Email(message = "Email address must be valid")
    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING) // Salva o nome textual do Enum
    @Column(name = "STATUS", nullable = false)
    private CustomerStatusEnum status;

    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDateTime birthday;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private WalletEntity wallet;
}
