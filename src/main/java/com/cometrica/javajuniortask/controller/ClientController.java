package com.cometrica.javajuniortask.controller;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.service.ClientService;

import com.cometrica.javajuniortask.validation.PageableConstraint;
import com.cometrica.javajuniortask.validation.SortPropertyConstraint;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping(
        value = "/clients",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Validated
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "")
    public ClientDTO addClient(@RequestParam("client_name") String name){
        Client client = clientService.addClient(name);
        ClientDTO dto = new ClientDTO();
        BeanUtils.copyProperties(client,dto);
        dto.setTotalDebt(BigDecimal.ZERO);
        return dto;
    }

    @GetMapping(value = "")
    @PageableAsQueryParam
    public PagedModel<EntityModel<ClientDTO>> getAllClientSummaries(@PageableConstraint(maxSize = 100, orders = {@SortPropertyConstraint("id"),@SortPropertyConstraint("name")})
                                                                    @Parameter(hidden = true) Pageable pageable,
                                                                    @Parameter(hidden = true) PagedResourcesAssembler<ClientDTO> assembler){
        Page<ClientDTO> page = clientService.getAllClientSummaries(pageable);
        return assembler.toModel(page);
    }

    @PostMapping(value = "debts")
    public DebtDTO addDebtToClient(@RequestParam("client_id") UUID clientId,
                                   @Positive(message = "debt_value must be > 0") @RequestParam("debt_value") BigDecimal value){
        Debt debt = clientService.addDebtToClient(clientId,value);
        DebtDTO dto = new DebtDTO();
        BeanUtils.copyProperties(debt,dto);
        return dto;
    }

    @PostMapping(value = "debts/payments")
    public PaymentDTO addPaymentToDebt(@RequestParam("debt_id") UUID debtId,
                                       @Positive(message = "payment_value must be > 0") @RequestParam("payment_value") BigDecimal value){
        Payment payment = clientService.addPaymentToDebt(debtId,value);
        PaymentDTO dto = new PaymentDTO();
        BeanUtils.copyProperties(payment,dto);
        return dto;
    }
}
