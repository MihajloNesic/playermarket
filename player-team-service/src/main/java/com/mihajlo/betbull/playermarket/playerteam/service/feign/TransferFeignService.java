package com.mihajlo.betbull.playermarket.playerteam.service.feign;

import com.mihajlo.betbull.playermarket.playerteam.config.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = TransferFeignService.TRANSFER_MICROSERVICE)
public interface TransferFeignService {

    String TRANSFER_MICROSERVICE = "transfer-service";

    @DeleteMapping(Path.API + Path.PLAYER_TRANSFER)
    void deleteByPlayerId(@PathVariable("playerId") Long playerId);

    @DeleteMapping(Path.API + Path.TEAM_TRANSFER)
    void deleteByTeamId(@PathVariable("teamId") Long teamId);

}
