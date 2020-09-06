package com.cometrica.javajuniortask._;

import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.repository.ClientRepository;
import com.cometrica.javajuniortask.service.ClientService;
import com.cometrica.javajuniortask.shell.ClientServiceCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@ShellComponent
public class TestCommands {
    @Autowired
    ClientServiceCommands csc;
    @Autowired
    ClientService cs;
    @ShellMethod("create user with debt=10")
    public void init() {
        u = csc.addClient("jo");
        d = csc.addDebtToClient(u,new BigDecimal(10));
    }
    UUID u;
    UUID d;
    @ShellMethod("pay payment=1")
    public void pay(){
        csc.addPaymentToDebt(d,BigDecimal.ONE);
    }

    @ShellMethod("init 50 clients by def ")
    public void initMany(@ShellOption(defaultValue = "50") int k){
        IntStream.range(1,++k).forEach(i-> {
            UUID id = csc.addClient(String.valueOf(i));
            cs.addDebtToClient(id,new BigDecimal(new Random().nextInt(100)));
        });

    }

    @Transactional
    @ShellMethod("showw")
    public void showw(){
//        Pageable pr = PageRequest.of(0,20, Sort.Direction.ASC, "name");
//        Page<Client> p;
//        do{
//            p = cs.getAll(pr);
//            p.forEach(c->System.out.println(c.getName()));
//            pr=p.nextOrLastPageable();
//        }while (p.hasNext());
    }

    @Autowired
    ClientRepository cr;

    @Transactional
    @ShellMethod("find")
    public String _find(){
//        Page<Object> c = cr.getAllClientSummaries(PageRequest.of(0,10, Sort.Direction.ASC,"totalDebt"));//+cr.find229().iterator().next().getIi();
//        return c.toString();
        return this.toString();
    }
}