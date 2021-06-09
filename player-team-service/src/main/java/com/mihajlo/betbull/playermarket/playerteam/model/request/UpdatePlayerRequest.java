package com.mihajlo.betbull.playermarket.playerteam.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdatePlayerRequest {

    @NotNull(message = "Player's first name is required")
    @Size(min = 2, message = "Player's first name must me minimum 2 characters long")
    private String firstName;

    @NotNull(message = "Player's last name is required")
    @Size(min = 2, message = "Player's last name must me minimum 2 characters long")
    private String lastName;

    @NotNull(message = "Player's birth date is required")
    @NotBlank(message = "Player's birth date is required")
    private String birthDate;

    @NotNull(message = "Player's career start date is required")
    @NotBlank(message = "Player's career start date is required")
    private String careerStartDate;

    public UpdatePlayerRequest() {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCareerStartDate() {
        return careerStartDate;
    }

    public void setCareerStartDate(String careerStartDate) {
        this.careerStartDate = careerStartDate;
    }
}