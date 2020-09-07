package com.cometrica.javajuniortask.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"client","payments"})
public class Debt {
    @Id
    private UUID id;
    private BigDecimal value;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "debt")
    @Cascade(CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();
    @Version
    private Integer version;

}
