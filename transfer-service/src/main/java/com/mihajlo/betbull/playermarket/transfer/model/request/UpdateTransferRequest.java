package com.mihajlo.betbull.playermarket.transfer.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateTransferRequest {

    @NotNull(message = "Team id is required")
    private Long teamId;

    @NotNull(message = "Transfer date is required")
    @NotBlank(message = "Transfer date is required")
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
