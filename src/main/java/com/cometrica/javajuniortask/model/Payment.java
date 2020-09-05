package com.cometrica.javajuniortask.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Payment {
    @Id
    private UUID id;
    private BigDecimal value;
    @ManyToOne
    private Debt debt;
}
