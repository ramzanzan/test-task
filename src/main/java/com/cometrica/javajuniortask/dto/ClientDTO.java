package com.cometrica.javajuniortask.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ClientDTO {
    private UUID id;
    private String name;
    private BigDecimal totalDebt;
}
