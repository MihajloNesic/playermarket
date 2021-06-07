package com.mihajlo.betbull.playermarket.transfer.service;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.TransferResponse;

public interface TransferService extends CrudService<Transfer, Long> {
    TransferResponse createTransfer(Long playerId, CreateTransferRequest request);
}
