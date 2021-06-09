package com.mihajlo.betbull.playermarket.playerteam.service.impl;

import com.mihajlo.betbull.playermarket.playerteam.entity.Team;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.Currency;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.TeamStatus;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.EntityException;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.InputException;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.TeamResponse;
import com.mihajlo.betbull.playermarket.playerteam.model.spec.TeamSpecifications;
import com.mihajlo.betbull.playermarket.playerteam.repository.TeamRepository;
import com.mihajlo.betbull.playermarket.playerteam.service.TeamService;
import com.mihajlo.betbull.playermarket.playerteam.service.feign.TransferFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    private TeamRepository teamRepository;
    private TransferFeignService transferFeignService;

    public TeamServiceImpl(TeamRepository teamRepository, TransferFeignService transferFeignService) {
        this.teamRepository = teamRepository;
        this.transferFeignService = transferFeignService;
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAllActive();
    }

    @Override
    public Page<TeamResponse> getAllActivePage(int page, int size) {
        Sort sort = Sort.by("name");
        Pageable pagingParams = PageRequest.of(page, size, sort);
        return teamRepository.findAll(TeamSpecifications.isActive(), pagingParams).map(TeamResponse::new);
    }

    @Override
    public List<TeamResponse> getAllActive() {
        return teamRepository.findAllActive().stream().map(TeamResponse::new).collect(Collectors.toList());
    }

    @Override
    public Team getById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (!team.isPresent()) {
            LOGGER.info("Team with id = {} was not found in the database.", id);
            throw new EntityException("Team with id " + id + " was not found");
        }
        return team.get();
    }

    @Override
    public TeamResponse getTeam(Long teamId) {
        Team team = getById(teamId);
        return new TeamResponse(team);
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void delete(Team team) {
        team.setStatus(TeamStatus.DELETED);
        teamRepository.save(team);
        transferFeignService.deleteByTeamId(team.getId());
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Deleting team with id = {}", id);
        Team team = getById(id);
        team.setStatus(TeamStatus.DELETED);
        teamRepository.save(team);
        transferFeignService.deleteByTeamId(id);
        LOGGER.info("Team with id = {} has been deleted (flagged)", id);
    }

    @Override
    public TeamResponse createTeam(CreateTeamRequest request) {
        Currency currency = null;
        try {
            currency = Currency.valueOf(request.getCurrency());
        }
        catch (IllegalArgumentException ex) {
            LOGGER.info("Currency with code '{}' does not exist", request.getCurrency());
            throw new InputException("Currency with code '"+request.getCurrency()+"' is not valid");
        }

        Team teamModel = Team.create(request.getName(), request.getCommissionPercentage(), currency);
        LOGGER.info("Creating team with name = {}", request.getName());
        Team newTeam = save(teamModel);
        LOGGER.info("Team with name = {} has been created, id = {}", newTeam.getName(), newTeam.getId());
        return new TeamResponse(newTeam);
    }

    @Override
    public TeamResponse updateTeam(Long teamId, UpdateTeamRequest request) {
        Currency currency = null;
        try {
            currency = Currency.valueOf(request.getCurrency());
        }
        catch (IllegalArgumentException ex) {
            LOGGER.info("Currency with code '{}' does not exist", request.getCurrency());
            throw new InputException("Currency with code '"+request.getCurrency()+"' is not valid");
        }

        Team team = getById(teamId);
        LOGGER.info("Updating team with id = {}", teamId);
        team.setName(request.getName());
        team.setCommissionPercentage(request.getCommissionPercentage());
        team.setCurrency(currency);
        Team updatedTeam = save(team);
        LOGGER.info("Team with id = {} has been updated", updatedTeam.getId());
        return new TeamResponse(updatedTeam);
    }
}
