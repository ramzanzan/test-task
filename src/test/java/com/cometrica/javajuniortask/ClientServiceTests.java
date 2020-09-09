package com.cometrica.javajuniortask;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.repository.ClientRepository;
import com.cometrica.javajuniortask.repository.DebtRepository;
import com.cometrica.javajuniortask.service.ClientService;
import liquibase.pro.packaged.E;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTests {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    DebtRepository debtRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ClientService clientService;
    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    public void addClientTest(){
        Client c = clientService.addClient("jojo");
        Assertions.assertEquals( "jojo",clientRepository.findById(c.getId()).get().getName());
    }

    @Test(expected = ConcurrencyFailureException.class)
    @Ignore
    public void addPaymentDoDebt_concurAccess() {
        //        ¯\_(ツ)_/¯
    }

    @Test
    public void addDebtToClient(){
        Client c = clientService.addClient("jojo");
        Debt d = clientService.addDebtToClient(c.getId(), BigDecimal.TEN);
        Assertions.assertEquals(BigDecimal.TEN, debtRepository.findById(d.getId()).get().getValue());
    }

    @Test
    public void addPaymentToDebt_save(){
        Client c = clientService.addClient("jojo");
        Debt d = clientService.addDebtToClient(c.getId(), BigDecimal.TEN);
        Payment p = clientService.addPaymentToDebt(d.getId(), BigDecimal.ONE);
        Assertions.assertEquals(BigDecimal.ONE,paymentRepository.findById(p.getId()).get().getValue());
    }

    @Test
    public void addPaymentToDebt_clientTotal(){
        Client c = clientService.addClient("jojo");
        Debt d = clientService.addDebtToClient(c.getId(), BigDecimal.TEN);
        Payment p = clientService.addPaymentToDebt(d.getId(), BigDecimal.ONE);
        Assertions.assertEquals(
                BigDecimal.TEN.subtract(BigDecimal.ONE),
                clientService.getAllClientSummaries(Pageable.unpaged()).iterator().next().getTotalDebt());
    }

    @Test
    public void addPaymentToDebt_debtVersion(){
        Client c = clientService.addClient("jojo");
        Debt d = clientService.addDebtToClient(c.getId(), BigDecimal.TEN);
        Payment p = clientService.addPaymentToDebt(d.getId(), BigDecimal.ONE);
        Assertions.assertEquals(1,debtRepository.findById(d.getId()).get().getVersion());
    }

}
