package com.mihajlo.betbull.playermarket.transfer.service;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.request.UpdateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerTeamResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TransferResponse;

import java.util.List;

public interface TransferService extends CrudService<Transfer, Long> {
    TransferResponse createTransfer(Long playerId, CreateTransferRequest request);
    TransferResponse updateTransfer(Long playerId, Long transferId, UpdateTransferRequest request);
    TransferResponse getTransfer(Long transferId);
    List<PlayerTeamResponse> getPlayerTeams(Long playerId);
    void deleteByPlayer(Long playerId);
    void deleteByTeam(Long teamId);
}
