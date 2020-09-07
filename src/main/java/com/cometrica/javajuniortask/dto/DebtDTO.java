package com.cometrica.javajuniortask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DebtDTO {
    private UUID id;
    private BigDecimal value;
}
