package com.cometrica.javajuniortask.model;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.*;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@EqualsAndHashCode(exclude = "debts")
@Table(name = "clients")
public class Client {
    @Id
    private UUID id;

    @Column(length = 128)
    private String name;
    @OneToMany(mappedBy = "client")
    @Cascade(CascadeType.ALL)
    Set<Debt> debts = new HashSet<>();
}
