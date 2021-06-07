package com.mihajlo.betbull.playermarket.transfer.repository;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends PagingAndSortingRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {

    @Query("select t from Transfer t where t.status != 'DELETED' order by t.transferDate")
    List<Transfer> findAllActive();

    @Query("select t from Transfer t where t.playerId = :playerId and t.status = 'ACTIVE'")
    Transfer findPlayersLastTransfer(@Param("playerId") Long playerId);

    @Query("select t from Transfer t where t.playerId = :playerId and t.status != 'DELETED' order by t.transferDate")
    List<Transfer> findPlayerTrasfers(@Param("playerId") Long playerId);
}

