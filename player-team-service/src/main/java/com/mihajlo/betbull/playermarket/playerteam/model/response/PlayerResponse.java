package com.mihajlo.betbull.playermarket.playerteam.model.response;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;

import java.time.LocalDate;

public class PlayerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate careerStartDate;

    public PlayerResponse() {
    }

    public PlayerResponse(Player player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.birthDate = player.getBirthDate();
        this.careerStartDate = player.getCareerStartDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getCareerStartDate() {
        return careerStartDate;
    }

    public void setCareerStartDate(LocalDate careerStartDate) {
        this.careerStartDate = careerStartDate;
    }
}
