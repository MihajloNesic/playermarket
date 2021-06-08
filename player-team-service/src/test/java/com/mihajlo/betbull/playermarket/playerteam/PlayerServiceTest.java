package com.mihajlo.betbull.playermarket.playerteam;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.PlayerStatus;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.EntityException;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.InputException;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.playerteam.repository.PlayerRepository;
import com.mihajlo.betbull.playermarket.playerteam.service.PlayerService;
import com.mihajlo.betbull.playermarket.playerteam.service.feign.TransferFeignService;
import com.mihajlo.betbull.playermarket.playerteam.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceTest.class);

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TransferFeignService transferFeignService;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl(playerRepository, transferFeignService);

    @Test
    public void createPlayerInvalidBirtDateError() {
        CreatePlayerRequest request = new CreatePlayerRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setBirthDate("11111111111");
        request.setCareerStartDate("1998-09-21");

        Exception exception = assertThrows(InputException.class, () -> playerService.createPlayer(request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void createPlayerInvalidCareerStartDAteError() {
        CreatePlayerRequest request = new CreatePlayerRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setBirthDate("1998-09-21");
        request.setCareerStartDate("11111111111");

        Exception exception = assertThrows(InputException.class, () -> playerService.createPlayer(request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void createPlayerSuccess() {
        when(playerRepository.save(any(Player.class))).thenAnswer(i -> {
            Player inserted = i.getArgument(0);
            inserted.setId(10L);
            return inserted;
        });

        CreatePlayerRequest request = new CreatePlayerRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setBirthDate("1998-09-21");
        request.setCareerStartDate("2016-12-04");

        PlayerResponse response = playerService.createPlayer(request);

        assertNotNull(response.getId());

        LocalDate birthDate = LocalDate.of(1998, Month.SEPTEMBER, 21);
        LocalDate careerStartDate = LocalDate.of(2016, Month.DECEMBER, 4);

        assertEquals(birthDate, response.getBirthDate());
        assertEquals(careerStartDate, response.getCareerStartDate());
    }

    @Test
    public void updatePlayerInvalidBirtDateError() {
        Long playerId = 5L;

        UpdatePlayerRequest request = new UpdatePlayerRequest();
        request.setFirstName("John 2");
        request.setLastName("Doe 2");
        request.setBirthDate("11111111111");
        request.setCareerStartDate("1999-09-21");

        Exception exception = assertThrows(InputException.class, () -> playerService.updatePlayer(playerId, request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void updatePlayerInvalidCareerStartDAteError() {
        Long playerId = 5L;

        UpdatePlayerRequest request = new UpdatePlayerRequest();
        request.setFirstName("John 2");
        request.setLastName("Doe 2");
        request.setBirthDate("1999-09-21");
        request.setCareerStartDate("11111111111");

        Exception exception = assertThrows(InputException.class, () -> playerService.updatePlayer(playerId, request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void updatePlayerNotExistingError() {
        Long playerId = 4L;

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        UpdatePlayerRequest request = new UpdatePlayerRequest();
        request.setFirstName("John 2");
        request.setLastName("Doe 2");
        request.setBirthDate("1999-10-22");
        request.setCareerStartDate("2017-01-05");

        Exception exception = assertThrows(EntityException.class, () -> playerService.updatePlayer(playerId, request));
        LOGGER.info("Exception message = {}", exception.getMessage());
    }

    @Test
    public void updatePlayerSuccess() {
        Long playerId = 5L;
        LocalDate birthDate = LocalDate.of(1998, Month.SEPTEMBER, 21);
        LocalDate careerStartDate = LocalDate.of(2016, Month.DECEMBER, 4);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(new Player(playerId, "John", "Doe", birthDate, careerStartDate, PlayerStatus.ACTIVE)));
        when(playerRepository.save(any(Player.class))).thenAnswer(i -> i.getArgument(0));

        UpdatePlayerRequest request = new UpdatePlayerRequest();
        request.setFirstName("John 2");
        request.setLastName("Doe 2");
        request.setBirthDate("1999-10-22");
        request.setCareerStartDate("2017-01-05");

        PlayerResponse response = playerService.updatePlayer(playerId, request);

        assertNotNull(response.getId());

        LocalDate birthDateUpdated = LocalDate.of(1999, Month.OCTOBER, 22);
        LocalDate careerStartDateUpdated = LocalDate.of(2017, Month.JANUARY, 5);

        assertEquals(birthDateUpdated, response.getBirthDate());
        assertEquals(careerStartDateUpdated, response.getCareerStartDate());
    }

}
