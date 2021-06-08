package com.mihajlo.betbull.playermarket.playerteam;

import com.mihajlo.betbull.playermarket.playerteam.entity.Team;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.Currency;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.TeamStatus;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.EntityException;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.InputException;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdateTeamRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.TeamResponse;
import com.mihajlo.betbull.playermarket.playerteam.repository.TeamRepository;
import com.mihajlo.betbull.playermarket.playerteam.service.TeamService;
import com.mihajlo.betbull.playermarket.playerteam.service.feign.TransferFeignService;
import com.mihajlo.betbull.playermarket.playerteam.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceTest.class);

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TransferFeignService transferFeignService;

    @InjectMocks
    private TeamService teamService = new TeamServiceImpl(teamRepository, transferFeignService);

    @Test
    public void createTeamInvalidCurrencyError() {
        CreateTeamRequest request = new CreateTeamRequest();
        request.setName("FC Best Team");
        request.setCommissionPercentage(7.00);
        request.setCurrency("TEST");

        Exception exception = assertThrows(InputException.class, () -> teamService.createTeam(request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void createTeamCommissionPercentageError() {
        CreateTeamRequest request = new CreateTeamRequest();
        request.setName("FC Best Team");
        request.setCommissionPercentage(25.00);
        request.setCurrency("USD");

        Exception exception = assertThrows(InputException.class, () -> teamService.createTeam(request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void createTeamSuccess() {
        // given
        when(teamRepository.save(any(Team.class))).thenAnswer(i -> {
            Team inserted = i.getArgument(0);
            inserted.setId(10L);
            return inserted;
        });

        CreateTeamRequest request = new CreateTeamRequest();
        request.setName("FC Best Team");
        request.setCommissionPercentage(7.57);
        request.setCurrency("USD");

        // when
        TeamResponse response = teamService.createTeam(request);

        // then
        assertNotNull(response.getId());
        assertEquals(Currency.USD, response.getCurrency());
    }

    @Test
    public void updateTeamNotExistingError() {
        Long teamId = 4L;

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        UpdateTeamRequest request = new UpdateTeamRequest();
        request.setName("FC Best Team 2");
        request.setCommissionPercentage(7.00);
        request.setCurrency("USD");

        Exception exception = assertThrows(EntityException.class, () -> teamService.updateTeam(teamId, request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void updateTeamInvalidCurrencyError() {
        Long teamId = 4L;
        UpdateTeamRequest request = new UpdateTeamRequest();
        request.setName("FC Best Team 2");
        request.setCommissionPercentage(7.00);
        request.setCurrency("TEST");

        Exception exception = assertThrows(InputException.class, () -> teamService.updateTeam(teamId, request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void updateTeamCommissionPercentageError() {
        Long teamId = 4L;

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(new Team(teamId, "FC Best Team", 7.57, Currency.USD, TeamStatus.ACTIVE)));

        UpdateTeamRequest request = new UpdateTeamRequest();
        request.setName("FC Best Team 2");
        request.setCommissionPercentage(34.00);
        request.setCurrency("USD");

        Exception exception = assertThrows(InputException.class, () -> teamService.updateTeam(teamId, request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void updateTeamSuccess() {
        Long teamId = 4L;

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(new Team(teamId, "FC Best Team", 7.57, Currency.USD, TeamStatus.ACTIVE)));
        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArgument(0));

        UpdateTeamRequest request = new UpdateTeamRequest();
        request.setName("FC Best Team 2");
        request.setCommissionPercentage(8.55);
        request.setCurrency("EUR");

        TeamResponse response = teamService.updateTeam(teamId, request);

        assertEquals(teamId, response.getId());
        assertEquals(Currency.EUR, response.getCurrency());
    }

}
