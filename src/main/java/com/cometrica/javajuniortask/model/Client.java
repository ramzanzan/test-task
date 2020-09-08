package com.cometrica.javajuniortask.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "clients")
public class Client {
    @Id
    private UUID id;

    @Column(length = 128)
    private String name;

    @OneToMany(mappedBy = "client")
    @Cascade(CascadeType.ALL)
    List<Debt> debts = new LinkedList<>();
}
