package com.mihajlo.betbull.playermarket.playerteam.entity;

import com.mihajlo.betbull.playermarket.playerteam.entity.domain.PlayerStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate careerStartDate;

    @Enumerated(EnumType.STRING)
    private PlayerStatus status;

    @Deprecated
    public Player() {
    }

    public static Player create(String firstName, String lastName, LocalDate birthDate, LocalDate careerStartDate) {
        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setBirthDate(birthDate);
        player.setCareerStartDate(careerStartDate);
        player.setStatus(PlayerStatus.ACTIVE);
        return player;
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

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public int getAge() {
        Period period = Period.between(birthDate, LocalDate.now());
        return period.getYears();
    }

}