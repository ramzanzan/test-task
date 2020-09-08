package com.cometrica.javajuniortask.service;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.repository.ClientRepository;
import com.cometrica.javajuniortask.repository.DebtRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final DebtRepository debtRepository;

    public ClientService(ClientRepository clientRepository, DebtRepository debtRepository) {
        this.clientRepository = clientRepository;
        this.debtRepository = debtRepository;
    }

    @Transactional
    public Page<ClientDTO> getAllClientSummaries(Pageable pageable) {
        return clientRepository.findAllWithPrefetchedRelationships(pageable)
                .map(client -> {
                    ClientDTO dto = new ClientDTO();
                    BeanUtils.copyProperties(client,dto);
                    dto.setTotalDebt(
                            client.getDebts().stream().map(
                                        debt -> debt.getValue().subtract(
                                                debt.getPayments().stream().map(Payment::getValue).reduce(BigDecimal.ZERO,BigDecimal::add)))
                            .reduce(BigDecimal.ZERO,BigDecimal::add));
                    return dto;
                });
    }

    /***
     * @param name name.length() must be > 0
     * @return
     */
    @Transactional
    public Client addClient(String name) {
        Client client = new Client();
        client.setName(name);
        client.setId(UUID.randomUUID());
        client = clientRepository.save(client);
        return client;
    }

    @Transactional
    public Debt addDebtToClient(UUID clientId,  BigDecimal value) {
        Client client = clientRepository.findById(clientId).orElseThrow(RuntimeException::new);
        Debt debt = new Debt();
        debt.setValue(value);
        debt.setId(UUID.randomUUID());
        debt.setClient(client);
        client.getDebts().add(debt);
        clientRepository.save(client);
        return debt;
    }

    @Transactional
    public Payment addPaymentToDebt(UUID debtId,  BigDecimal value){
        Debt debt = debtRepository.findByIdWithOptimisticForceIncrementLock(debtId).orElseThrow(RuntimeException::new);
        if(debt.getPayments().stream().map(Payment::getValue).reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(value).compareTo(debt.getValue()) > 0)
            throw new RuntimeException();
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setDebt(debt);
        payment.setValue(value);
        debt.getPayments().add(payment);
        debtRepository.save(debt);
        return payment;
    }

}
