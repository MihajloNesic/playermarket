package com.mihajlo.betbull.playermarket.transfer.service.feign;

import com.mihajlo.betbull.playermarket.transfer.config.Path;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TeamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = PlayerTeamFeignService.PLAYER_TEAM_MICROSERVICE)
public interface PlayerTeamFeignService {

    String PLAYER_TEAM_MICROSERVICE = "player-team-service";

    @GetMapping(Path.API + Path.PLAYER_SINGLE)
    PlayerResponse getPlayerById(@PathVariable("playerId") Long playerId);

    @GetMapping(Path.API + Path.TEAM_SINGLE)
    TeamResponse getTeamById(@PathVariable("teamId") Long id);

}
