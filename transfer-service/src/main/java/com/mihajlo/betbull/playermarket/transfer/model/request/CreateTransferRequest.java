package com.mihajlo.betbull.playermarket.transfer.model.request;

public class CreateTransferRequest {

    private Long teamId;
    private String transferDate;

    public CreateTransferRequest() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}
