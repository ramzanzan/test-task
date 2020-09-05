package com.cometrica.javajuniortask.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Debt {
    @Id
    private UUID id;
    private BigDecimal value;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "debt")
    @Cascade(CascadeType.ALL)
    private List<Payment> payments = new LinkedList<>();
    @Version
    @Setter(AccessLevel.PRIVATE)
    private Integer version;

}
