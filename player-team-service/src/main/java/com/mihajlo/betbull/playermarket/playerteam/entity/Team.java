package com.mihajlo.betbull.playermarket.playerteam.entity;

import com.mihajlo.betbull.playermarket.playerteam.entity.domain.Currency;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.TeamStatus;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.InputException;

import javax.persistence.*;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double commissionPercentage;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    @Deprecated
    public Team() {
    }

    @Deprecated
    public Team(Long id, String name, Double commissionPercentage, Currency currency, TeamStatus status) {
        this.id = id;
        this.name = name;
        this.commissionPercentage = commissionPercentage;
        this.currency = currency;
        this.status = status;
    }

    public static Team create(String name, Double commissionPercentage, Currency currency) {
        Team team = new Team();
        team.setName(name);
        team.setCommissionPercentage(commissionPercentage);
        team.setCurrency(currency);
        team.setStatus(TeamStatus.ACTIVE);
        return team;
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
        if (commissionPercentage < 0 || commissionPercentage > 10.0) {
            throw new InputException("Team's commission percentage must be between 0% and 10%");
        }
        this.commissionPercentage = commissionPercentage;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }
}