package com.mihajlo.betbull.playermarket.playerteam.service;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.PlayerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlayerService extends CrudService<Player, Long> {
    List<PlayerResponse> getAllActive();
    Page<PlayerResponse> getAllActivePage(int page, int size);
    PlayerResponse createPlayer(CreatePlayerRequest request);
    PlayerResponse updatePlayer(Long playerId, UpdatePlayerRequest request);
}

