package com.cometrica.javajuniortask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String status;
    private int code;
    private String[] errors;

    public ErrorDTO(HttpStatus status, String... errors){
        setStatus(status.name());
        setCode(status.value());
        setErrors(errors);
    }
}
