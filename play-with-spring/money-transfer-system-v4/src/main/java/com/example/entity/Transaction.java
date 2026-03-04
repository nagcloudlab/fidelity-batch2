package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long id;
//    @Column(name = "account_number", nullable = false)
//    private String accountNumber;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;
    private double amount;
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type; // e.g., "DEPOSIT", "WITHDRAWAL"
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;
    private String description;

    // Constructors, getters, and setters
}
