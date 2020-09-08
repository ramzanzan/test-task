package com.cometrica.javajuniortask.model;

import javax.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
@EqualsAndHashCode(exclude = {"client","payments"})
@Entity
@Table(name = "debts")
public class Debt {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "debt")
    @Cascade(CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();

    @Version
    @Column(nullable = false)
    private Integer version;

}
