package com.cometrica.javajuniortask.repository;

import com.cometrica.javajuniortask.model.Debt;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.LockModeType;

@Repository
public interface DebtRepository extends CrudRepository<Debt, UUID> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("select d from Debt d where d.id=:id")
    Optional<Debt> findByIdWithOptimisticForceIncLock(@Param("id") UUID id);
}
