package com.mihajlo.betbull.playermarket.transfer.controller;

import com.mihajlo.betbull.playermarket.transfer.config.Path;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.TransferResponse;
import com.mihajlo.betbull.playermarket.transfer.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Path.API)
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = Path.PLAYER_TRANSFER)
    public ResponseEntity<TransferResponse> getActivePlayers(@PathVariable("playerId") Long playerId, @RequestBody CreateTransferRequest request) {
        return ResponseEntity.ok(transferService.createTransfer(playerId, request));
    }

//    @GetMapping(value = Path.PLAYER_TEAMS)
//    public ResponseEntity<List<PlayerTransferResponse>> getPlayerTeams(@PathVariable("playerId") Long playerId) {
//        return ResponseEntity.ok(transferService.getPlayerTeams(playerId));
//    }
}
