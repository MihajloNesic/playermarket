package com.mihajlo.betbull.playermarket.playerteam.controller;

import com.mihajlo.betbull.playermarket.playerteam.config.Path;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.playerteam.model.response.SimpleMessageResponse;
import com.mihajlo.betbull.playermarket.playerteam.service.PlayerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(Path.API)
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = Path.PLAYERS)
    @ApiOperation("Returns all active players; with pagination")
    public ResponseEntity<Page<PlayerResponse>> getActivePlayers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(playerService.getAllActivePage(page, size));
    }

    @GetMapping(value = Path.PLAYER_SINGLE)
    @ApiOperation("Returns a player by ID")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable("playerId") Long playerId) {
        return ResponseEntity.ok(playerService.getPlayer(playerId));
    }

    @PostMapping(value = Path.PLAYERS)
    @ApiOperation("Creates a new player")
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        return ResponseEntity.ok(playerService.createPlayer(request));
    }

    @PutMapping(value = Path.PLAYER_SINGLE)
    @ApiOperation("Updates an existing player")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable("playerId") Long playerId, @Valid @RequestBody UpdatePlayerRequest request) {
        return ResponseEntity.ok(playerService.updatePlayer(playerId, request));
    }

    @DeleteMapping(value = Path.PLAYER_SINGLE)
    @ApiOperation("Deletes* an existing player (*flags as deleted)")
    public ResponseEntity<SimpleMessageResponse> deletePlayer(@PathVariable("playerId") Long playerId) {
        playerService.deleteById(playerId);
        return ResponseEntity.ok(new SimpleMessageResponse("Player has been deleted successfully"));
    }

}
