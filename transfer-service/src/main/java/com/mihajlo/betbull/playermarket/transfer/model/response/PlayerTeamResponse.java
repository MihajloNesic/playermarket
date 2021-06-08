package com.mihajlo.betbull.playermarket.transfer.model.response;

public class PlayerTeamResponse {

    private Long teamId;
    private String teamName;

    public PlayerTeamResponse() {
    }

    public PlayerTeamResponse(TeamResponse teamResponse) {
        this.teamId = teamResponse.getId();
        this.teamName = teamResponse.getName();
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}