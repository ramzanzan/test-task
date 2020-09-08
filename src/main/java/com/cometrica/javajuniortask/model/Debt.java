package com.cometrica.javajuniortask.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "debts")
public class Debt {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;
}
