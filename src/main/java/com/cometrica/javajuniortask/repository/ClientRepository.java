package com.cometrica.javajuniortask.repository;

import com.cometrica.javajuniortask.model.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, UUID> {

    @Transactional(readOnly = true)
    @Query("select c from Client c")
    @EntityGraph(attributePaths = "debts")
    Page<Client> findAllWithPrefetchedRelationships(Pageable pageable);

}
