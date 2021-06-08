package com.mihajlo.betbull.playermarket.transfer.repository;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface TransferRepository extends PagingAndSortingRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {

    @Query("select t from Transfer t where t.status != 'DELETED' order by t.transferDate")
    List<Transfer> findAllActive();

    //@Query("select t from Transfer t where t.playerId = :playerId and t.status = 'ACTIVE'")
    @Query("select t from Transfer t " +
            "where t.playerId = :playerId " +
            "and t.transferDate = (select max(pt.transferDate) from Transfer pt where pt.playerId = :playerId and pt.status != 'DELETED')" +
            "and t.status != 'DELETED'")
    Transfer findPlayersLastTransfer(@Param("playerId") Long playerId);

    @Query("select t from Transfer t where t.playerId = :playerId and t.id = :transferId")
    Transfer findPlayersTransfer(@Param("playerId") Long playerId, @Param("transferId") Long transferId);

    @Query("select t.teamId from Transfer t where t.playerId = :playerId and t.status != 'DELETED'")
    Set<Long> findPlayerTeams(@Param("playerId") Long playerId);

    @Modifying
    @Transactional
    @Query("update Transfer t set t.status = 'DELETED' where t.playerId = :playerId")
    void deleteByPlayer(@Param("playerId") Long playerId);

    @Modifying
    @Transactional
    @Query("update Transfer t set t.status = 'DELETED' where t.teamId = :teamId")
    void deleteByTeam(@Param("teamId") Long teamId);
}

