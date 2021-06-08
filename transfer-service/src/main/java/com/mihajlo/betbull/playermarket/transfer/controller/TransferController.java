package com.mihajlo.betbull.playermarket.transfer.controller;

import com.mihajlo.betbull.playermarket.transfer.config.Path;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.request.UpdateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerTeamResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.SimpleMessageResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TransferResponse;
import com.mihajlo.betbull.playermarket.transfer.service.TransferService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Path.API)
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = Path.PLAYER_TRANSFER)
    @ApiOperation("Creates a new transfer for player")
    public ResponseEntity<TransferResponse> createTransfer(@PathVariable("playerId") Long playerId, @RequestBody CreateTransferRequest request) {
        return ResponseEntity.ok(transferService.createTransfer(playerId, request));
    }

    @PutMapping(value = Path.PLAYER_TRANSFER_SINGLE)
    @ApiOperation("Updates an existing transfer for player")
    public ResponseEntity<TransferResponse> updatePlayersTransfer(@PathVariable("playerId") Long playerId, @PathVariable("transferId") Long transferId, @RequestBody UpdateTransferRequest request) {
        return ResponseEntity.ok(transferService.updateTransfer(playerId, transferId, request));
    }

    @GetMapping(value = Path.TRANSFER_SINGLE)
    @ApiOperation("Returns a transfer by ID")
    public ResponseEntity<TransferResponse> getTransfer(@PathVariable("transferId") Long transferId) {
        return ResponseEntity.ok(transferService.getTransfer(transferId));
    }

    @GetMapping(value = Path.PLAYER_TEAMS)
    @ApiOperation("Returns all player's teams")
    public ResponseEntity<List<PlayerTeamResponse>> getPlayerTeams(@PathVariable("playerId") Long playerId) {
        return ResponseEntity.ok(transferService.getPlayerTeams(playerId));
    }

    @DeleteMapping(value = Path.PLAYER_TRANSFER)
    @ApiOperation("Deletes all transfers for player")
    public ResponseEntity<SimpleMessageResponse> deleteByPlayer(@PathVariable("playerId") Long playerId) {
        transferService.deleteByPlayer(playerId);
        return ResponseEntity.ok(new SimpleMessageResponse("Player's transfers has been deleted"));
    }

    @DeleteMapping(value = Path.TEAM_TRANSFER)
    @ApiOperation("Deletes all transfers for team")
    public ResponseEntity<SimpleMessageResponse> deleteByTeam(@PathVariable("teamId") Long teamId) {
        transferService.deleteByTeam(teamId);
        return ResponseEntity.ok(new SimpleMessageResponse("Team's transfers has been deleted"));
    }
}
