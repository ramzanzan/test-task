package com.cometrica.javajuniortask.repository;

import com.cometrica.javajuniortask.model.Client;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, UUID> {
}
