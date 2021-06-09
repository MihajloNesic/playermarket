package com.mihajlo.betbull.playermarket.playerteam.service.impl;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.PlayerStatus;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.EntityException;
import com.mihajlo.betbull.playermarket.playerteam.exception.error.InputException;
import com.mihajlo.betbull.playermarket.playerteam.model.request.CreatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.request.UpdatePlayerRequest;
import com.mihajlo.betbull.playermarket.playerteam.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.playerteam.model.spec.PlayerSpecifications;
import com.mihajlo.betbull.playermarket.playerteam.repository.PlayerRepository;
import com.mihajlo.betbull.playermarket.playerteam.service.PlayerService;
import com.mihajlo.betbull.playermarket.playerteam.service.feign.TransferFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private PlayerRepository playerRepository;
    private TransferFeignService transferFeignService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TransferFeignService transferFeignService) {
        this.playerRepository = playerRepository;
        this.transferFeignService = transferFeignService;
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAllActive();
    }

    @Override
    public Page<PlayerResponse> getAllActivePage(int page, int size) {
        Sort sort = Sort.by("firstName").and(Sort.by("lastName"));
        Pageable pagingParams = PageRequest.of(page, size, sort);
        return playerRepository.findAll(PlayerSpecifications.isActive(), pagingParams).map(PlayerResponse::new);
    }

    @Override
    public List<PlayerResponse> getAllActive() {
        return playerRepository.findAllActive().stream().map(PlayerResponse::new).collect(Collectors.toList());
    }

    @Override
    public Player getById(Long id) {
        Optional<Player> player = playerRepository.findById(id);
        if (!player.isPresent()) {
            LOGGER.info("Player with id = {} was not found in the database.", id);
            throw new EntityException("Player with id " + id + " was not found");
        }
        return player.get();
    }

    @Override
    public PlayerResponse getPlayer(Long id) {
        Player player = getById(id);
        return new PlayerResponse(player);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void delete(Player player) {
        player.setStatus(PlayerStatus.DELETED);
        playerRepository.save(player);
        transferFeignService.deleteByPlayerId(player.getId());
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Deleting player with id = {}", id);
        Player player = getById(id);
        player.setStatus(PlayerStatus.DELETED);
        playerRepository.save(player);
        transferFeignService.deleteByPlayerId(id);
        LOGGER.info("Player with id = {} has been deleted (flagged)", id);
    }

    @Override
    public PlayerResponse createPlayer(CreatePlayerRequest request) {
        LocalDate playerBirthDate = null;
        LocalDate careerStartDate = null;

        try {
            playerBirthDate = LocalDate.parse(request.getBirthDate());
            careerStartDate = LocalDate.parse(request.getCareerStartDate());
        }
        catch (DateTimeParseException ex) {
            LOGGER.info("Could not parse date = {}", request.getBirthDate());
            throw new InputException("You've entered an invalid date. Please enter date in format 'YYYY-MM-DD'");
        }

        Player playerModel = Player.create(request.getFirstName(), request.getLastName(), playerBirthDate, careerStartDate);
        LOGGER.info("Creating player");
        Player newPlayer = save(playerModel);
        LOGGER.info("Player with name = {} has been created, id = {}", newPlayer.getFirstName(), newPlayer.getId());
        return new PlayerResponse(newPlayer);
    }


    @Override
    public PlayerResponse updatePlayer(Long playerId, UpdatePlayerRequest request) {
        LocalDate playerBirthDate = null;
        LocalDate careerStartDate = null;

        try {
            playerBirthDate = LocalDate.parse(request.getBirthDate());
            careerStartDate = LocalDate.parse(request.getCareerStartDate());
        }
        catch (DateTimeParseException ex) {
            LOGGER.info("Could not parse date = {}", request.getBirthDate());
            throw new InputException("You've entered an invalid date. Please enter date in format 'YYYY-MM-DD'");
        }

        Player player = getById(playerId);
        player.setFirstName(request.getFirstName());
        player.setLastName(request.getLastName());
        player.setBirthDate(playerBirthDate);
        player.setCareerStartDate(careerStartDate);
        Player updatedPlayer = save(player);
        LOGGER.info("Player with id = {} has been updated", updatedPlayer.getId());

        return new PlayerResponse(updatedPlayer);
    }

}
