package com.cometrica.javajuniortask.repository;

import com.cometrica.javajuniortask.model.Debt;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.LockModeType;

@Repository
public interface DebtRepository extends CrudRepository<Debt, UUID> {

    @Transactional
    @Query("select d from Debt d where d.id=?1")
//    can't be used because of bug in hibernate
//    @EntityGraph(attributePaths = "payments")
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Debt> findByIdWithOptimisticForceIncrementLock(UUID id);
}
