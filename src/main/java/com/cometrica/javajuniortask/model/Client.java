package com.cometrica.javajuniortask.model;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
@EqualsAndHashCode(exclude = "debts")
public class Client {
    @Id
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "client")
    @Cascade(CascadeType.ALL)
    Set<Debt> debts = new HashSet<>();
}
