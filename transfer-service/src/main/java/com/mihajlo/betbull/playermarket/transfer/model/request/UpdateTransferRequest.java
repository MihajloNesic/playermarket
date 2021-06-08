package com.mihajlo.betbull.playermarket.transfer.model.request;

public class UpdateTransferRequest {

    private Long teamId;
    private String transferDate;

    public UpdateTransferRequest() {
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
