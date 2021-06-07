package com.mihajlo.betbull.playermarket.transfer.entity;

import com.mihajlo.betbull.playermarket.transfer.entity.domain.Currency;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.TransferStatus;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TeamResponse;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long playerId;
    private Long teamId;
    private LocalDate transferDate;
    private Double transferFee;
    private Double commissionPercentage;
    private Double teamCommission;
    private Double contractFee;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @Deprecated
    public Transfer() {
    }

    public static Transfer create(PlayerResponse player, TeamResponse team, LocalDate transferDate, Double transferFee, Double teamCommission, Double contractFee) {
        Transfer transfer = new Transfer();
        transfer.setPlayerId(player.getId());
        transfer.setTeamId(team.getId());
        transfer.setTransferDate(transferDate);
        transfer.setTransferFee(transferFee);
        transfer.setCommissionPercentage(team.getCommissionPercentage());
        transfer.setTeamCommission(teamCommission);
        transfer.setContractFee(contractFee);
        transfer.setCurrency(team.getCurrency());
        transfer.setStatus(TransferStatus.ACTIVE);
        return transfer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
