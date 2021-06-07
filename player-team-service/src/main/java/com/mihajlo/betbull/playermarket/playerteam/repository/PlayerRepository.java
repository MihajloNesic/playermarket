package com.mihajlo.betbull.playermarket.playerteam.repository;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;
import com.mihajlo.betbull.playermarket.playerteam.model.response.PlayerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select p from Player p where p.status = 'ACTIVE' order by p.firstName, p.lastName")
    List<Player> findAllActive();

    @Query("select new com.mihajlo.betbull.playermarket.playerteam.model.response.PlayerResponse(p) " +
            "from Player p " +
            "where p.status = 'ACTIVE' " +
            "order by p.firstName, p.lastName")
    List<PlayerResponse> findAllActiveResp();
}
