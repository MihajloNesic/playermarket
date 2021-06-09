package com.mihajlo.betbull.playermarket.playerteam.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateTeamRequest {

    @NotNull(message = "Team name is required")
    @Size(min = 2, message = "Team name must me minimum 2 characters long")
    private String name;

    @NotNull(message = "Team's commission percentage is required")
    private Double commissionPercentage;

    @NotNull(message = "Team's preferred currency is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters long")
    private String currency;

    public UpdateTeamRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}