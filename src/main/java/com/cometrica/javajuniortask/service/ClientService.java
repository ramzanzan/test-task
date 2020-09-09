package com.cometrica.javajuniortask.service;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.repository.ClientRepository;
import com.cometrica.javajuniortask.repository.DebtRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.NonTransientDataAccessException;
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
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->new DataRetrievalFailureException("client with id="+clientId+" doesn't exits"));
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
        Debt debt = debtRepository.findByIdWithOptimisticForceIncrementLock(debtId)
                .orElseThrow(()->new DataRetrievalFailureException("debt with id="+debtId+" doesn't exits"));
        BigDecimal currentTotalPayments = debt.getPayments().stream().map(Payment::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(currentTotalPayments.add(value).compareTo(debt.getValue()) > 0)
            throw new DataIntegrityViolationException("sum of payments exceed debt; "+
                    "max allowed payment value="+debt.getValue().subtract(currentTotalPayments));
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setDebt(debt);
        payment.setValue(value);
        debt.getPayments().add(payment);
        debtRepository.save(debt);
        return payment;
    }

}
