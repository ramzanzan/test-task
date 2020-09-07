package com.cometrica.javajuniortask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private BigDecimal value;
}
