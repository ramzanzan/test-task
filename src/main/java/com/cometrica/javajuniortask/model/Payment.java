package com.cometrica.javajuniortask.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(exclude = "debt")
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne(optional = false)
    private Debt debt;
}
