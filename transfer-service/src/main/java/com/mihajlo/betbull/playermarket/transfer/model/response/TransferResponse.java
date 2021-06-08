package com.mihajlo.betbull.playermarket.transfer.model.response;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.Currency;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.TransferStatus;

import java.time.LocalDate;

public class TransferResponse {

    private Long transferId;
    private Long playerId;
    private Long teamId;
    private LocalDate transferDate;
    private Double transferFee;
    private Double commissionPercentage;
    private Double teamCommission;
    private Double contractFee;
    private Currency currency;
    private TransferStatus status;

    public TransferResponse() {
    }

    public TransferResponse(Transfer transfer) {
        this.transferId = transfer.getId();
        this.playerId = transfer.getPlayerId();
        this.teamId = transfer.getTeamId();
        this.transferDate = transfer.getTransferDate();
        this.transferFee = transfer.getTransferFee();
        this.commissionPercentage = transfer.getCommissionPercentage();
        this.teamCommission = transfer.getTeamCommission();
        this.contractFee = transfer.getContractFee();
        this.currency = transfer.getCurrency();
        this.status = transfer.getStatus();
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public Double getTeamCommission() {
        return teamCommission;
    }

    public void setTeamCommission(Double teamCommission) {
        this.teamCommission = teamCommission;
    }

    public Double getContractFee() {
        return contractFee;
    }

    public void setContractFee(Double contractFee) {
        this.contractFee = contractFee;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }
}
