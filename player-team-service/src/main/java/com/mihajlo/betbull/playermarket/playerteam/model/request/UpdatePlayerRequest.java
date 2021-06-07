package com.mihajlo.betbull.playermarket.playerteam.model.request;

public class UpdatePlayerRequest {

    private String firstName;
    private String lastName;
    private String birthDate;
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