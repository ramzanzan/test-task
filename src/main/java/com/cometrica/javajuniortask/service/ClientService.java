package com.cometrica.javajuniortask.service;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.repository.ClientRepository;

import com.cometrica.javajuniortask.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ShellComponent
public class ClientService {
    private final ClientRepository clientRepository;
    private final DebtRepository debtRepository;

    public ClientService(ClientRepository clientRepository, DebtRepository debtRepository) {
        this.clientRepository = clientRepository;
        this.debtRepository = debtRepository;
    }

    @ShellMethod("Shows all clients in db")
    @Transactional
    public Iterable<ClientDTO> showAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).map(client -> {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName(client.getName());
            clientDTO.setTotalDebt(client.getDebts().stream()
                    .map(debt -> debt.getValue().subtract(
                            debt.getPayments().stream().map(Payment::getValue).reduce(BigDecimal.ZERO,BigDecimal::add)))
                    .reduce(BigDecimal.ZERO,BigDecimal::add));
            return clientDTO;
        }).collect(Collectors.toList());
    }

    @ShellMethod("Adds client to db")
    @Transactional
    public UUID addClient(@ShellOption String name) {
        Client client = new Client();
        client.setName(name);
        client.setId(UUID.randomUUID());
        client = clientRepository.save(client);
        return client.getId();
    }

    @ShellMethod("Adds debt to client")
    @Transactional
    public UUID addDebtToClient(@ShellOption UUID clientId, @ShellOption BigDecimal value) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        Debt debt = new Debt();
        debt.setValue(value);
        debt.setId(UUID.randomUUID());
        debt.setClient(client);
        client.getDebts().add(debt);
        clientRepository.save(client);
        return debt.getId();
    }

    @ShellMethod("Adds payment to debt")
    @Transactional
    public UUID addPaymentToDebt(@ShellOption UUID debtId, @ShellOption BigDecimal value){
        Debt debt = debtRepository.findByIdWithOptimisticForceIncLock(debtId).orElseThrow(RuntimeException::new);
        if(debt.getPayments().stream().map(Payment::getValue).reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(value).compareTo(debt.getValue()) > 0)
            throw new RuntimeException();
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setDebt(debt);
        payment.setValue(value);
        debt.getPayments().add(payment);
        debtRepository.save(debt);
        return payment.getId();
    }

}
