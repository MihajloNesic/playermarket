package com.mihajlo.betbull.playermarket.playerteam.config;

public class Path {

    public static final String API = "/api";

    // Team
    public static final String TEAMS = "/teams";
    public static final String TEAM_ID = "{teamId}";
    public static final String TEAM_SINGLE = TEAMS + "/" + TEAM_ID;

    // Player
    public static final String PLAYERS = "/players";
    public static final String PLAYER_ID = "{playerId}";
    public static final String PLAYER_SINGLE = PLAYERS + "/" + PLAYER_ID;
    public static final String PLAYER_TEAMS = PLAYER_SINGLE + TEAMS;

    // Transfer
    public static final String TRANSFER = "/transfer";
    public static final String PLAYER_TRANSFER = PLAYER_SINGLE + TRANSFER;
    public static final String TEAM_TRANSFER = TEAM_SINGLE + TRANSFER;

}
