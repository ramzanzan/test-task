package com.cometrica.javajuniortask.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Length;

import java.util.*;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "debts")
@Entity
@Table(name = "clients")
public class Client {
    @Id
    private UUID id;

    @Column(length = 128)
    @Length(min = 1, max = 128)
    private String name;

    @OneToMany(mappedBy = "client")
    @Cascade(CascadeType.ALL)
    Set<Debt> debts = new HashSet<>();
}


