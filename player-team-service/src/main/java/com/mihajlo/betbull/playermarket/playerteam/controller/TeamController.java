package com.mihajlo.betbull.playermarket.playerteam.controller;

import com.mihajlo.betbull.playermarket.playerteam.config.Path;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.SimpleMessageResponse;
import com.mihajlo.betbull.playermarket.playerteam.model.response.TeamResponse;
import com.mihajlo.betbull.playermarket.playerteam.service.TeamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Path.API)
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = Path.TEAMS)
    @ApiOperation("Returns all active teams; with pagination")
    public ResponseEntity<Page<TeamResponse>> getActiveTeams(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(teamService.getAllActivePage(page, size));
    }

    @GetMapping(value = Path.TEAM_SINGLE)
    @ApiOperation("Returns a team by ID")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @PostMapping(value = Path.TEAMS)
    @ApiOperation("Creates a new team")
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody CreateTeamRequest request) {
        return ResponseEntity.ok(teamService.createTeam(request));
    }

    @PutMapping(value = Path.TEAM_SINGLE)
    @ApiOperation("Updates an existing team")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable("teamId") Long teamId, @Valid @RequestBody UpdateTeamRequest request) {
        return ResponseEntity.ok(teamService.updateTeam(teamId, request));
    }

    @DeleteMapping(value = Path.TEAM_SINGLE)
    @ApiOperation("Deletes* an existing team (*flags as deleted)")
    public ResponseEntity<SimpleMessageResponse> deleteTeam(@PathVariable("teamId") Long teamId) {
        teamService.deleteById(teamId);
        return ResponseEntity.ok(new SimpleMessageResponse("Team has been deleted successfully"));
    }
}
