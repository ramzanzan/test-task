package com.cometrica.javajuniortask.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity
@Data
@EqualsAndHashCode(exclude = "debt")
public class Payment {
    @Id
    private UUID id;
    private BigDecimal value;
    @ManyToOne
    private Debt debt;
}
