package com.mihajlo.betbull.playermarket.playerteam.repository;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long>, JpaSpecificationExecutor<Player> {

    @Query("select p from Player p where p.status = 'ACTIVE' order by p.firstName, p.lastName")
    List<Player> findAllActive();
}
