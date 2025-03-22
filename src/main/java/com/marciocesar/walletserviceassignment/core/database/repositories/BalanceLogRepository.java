package com.marciocesar.walletserviceassignment.core.database.repositories;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BalanceLogRepository extends JpaRepository<BalanceLogEntity, BalanceLogEntity.BalanceLogId> {

    @Query("""
            SELECT b
            FROM BalanceLogEntity b
            WHERE b.updateDate IN (
                SELECT MAX(bl.updateDate)
                FROM BalanceLogEntity bl
                WHERE bl.updateDate BETWEEN :startDate AND :endDate
                GROUP BY CAST(bl.updateDate as DATE)
            )
            and b.walletId = :walletId
            ORDER BY b.updateDate DESC
            """)
    Page<BalanceLogEntity> findLastDailyBalanceByPeriod(
            @Param("walletId") Long walletId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
