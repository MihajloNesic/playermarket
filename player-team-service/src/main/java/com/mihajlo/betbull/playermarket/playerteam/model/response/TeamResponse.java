package com.mihajlo.betbull.playermarket.playerteam.model.response;

import com.mihajlo.betbull.playermarket.playerteam.entity.Team;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.Currency;

public class TeamResponse {

    private Long id;
    private String name;
    private Double commissionPercentage;
    private Currency currency;

    public TeamResponse() {
    }

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.commissionPercentage = team.getCommissionPercentage();
        this.currency = team.getCurrency();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}