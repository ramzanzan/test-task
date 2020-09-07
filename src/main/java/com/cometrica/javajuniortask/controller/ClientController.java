package com.cometrica.javajuniortask.controller;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ClientDTO addClient(@RequestParam("name") String name){
        Client client = clientService.addClient(name);
        ClientDTO dto = new ClientDTO();
        BeanUtils.copyProperties(client,dto);
        return dto;
    }

    @GetMapping(value = "",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PagedModel<EntityModel<ClientDTO>> getAllClientSummaries(Pageable pageable, PagedResourcesAssembler<ClientDTO> assembler){
        Page<ClientDTO> page = clientService.getAllClientSummaries(pageable);
        return assembler.toModel(page);
    }

    @PostMapping(value = "debts",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DebtDTO addDebtToClient(@RequestParam("client_id") UUID clientId, @RequestParam("value") BigDecimal value){
        Debt debt = clientService.addDebtToClient(clientId,value);
        DebtDTO dto = new DebtDTO();
        BeanUtils.copyProperties(debt,dto);
        return dto;
    }

    @PostMapping(value = "debts/payments",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PaymentDTO addPaymentToDebt(@RequestParam("debt_id") UUID debtId, @RequestParam("value") BigDecimal value){
        Payment payment = clientService.addPaymentToDebt(debtId,value);
        PaymentDTO dto = new PaymentDTO();
        return dto;
    }
}
//todo valid
