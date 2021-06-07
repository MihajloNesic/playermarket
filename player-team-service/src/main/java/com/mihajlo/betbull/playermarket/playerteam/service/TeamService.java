package com.mihajlo.betbull.playermarket.playerteam.service;

import com.mihajlo.betbull.playermarket.playerteam.entity.Team;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.TeamResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeamService extends CrudService<Team, Long> {
    List<TeamResponse> getAllActive();
    Page<TeamResponse> getAllActivePage(int page, int size);
    TeamResponse getTeam(Long teamId);
    TeamResponse createTeam(CreateTeamRequest request);
    TeamResponse updateTeam(Long teamId, UpdateTeamRequest request);
}
