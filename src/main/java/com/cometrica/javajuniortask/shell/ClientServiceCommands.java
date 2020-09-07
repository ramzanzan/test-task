package com.cometrica.javajuniortask.shell;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.service.ClientService;

import org.springframework.data.domain.Pageable;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.util.UUID;

@ShellComponent
public class ClientServiceCommands {

    private final ClientService clientService;

    public ClientServiceCommands(ClientService clientService) {
        this.clientService = clientService;
    }

    @ShellMethod("Shows all clients in db")
    public Iterable<ClientDTO> showAllClients() {
        return clientService.getAllClientSummaries(Pageable.unpaged());
    }

    @ShellMethod("Adds client to db")
    public UUID addClient(@ShellOption String name) {
        return clientService.addClient(name).getId();
    }

    @ShellMethod("Adds debt to client")
    public UUID addDebtToClient(@ShellOption UUID clientId, @ShellOption BigDecimal value) {
        return clientService.addDebtToClient(clientId,value).getId();
    }

    @ShellMethod("Adds payment to debt")
    public UUID addPaymentToDebt(@ShellOption UUID debtId, @ShellOption BigDecimal value){
        return clientService.addPaymentToDebt(debtId,value).getId();
    }

}
