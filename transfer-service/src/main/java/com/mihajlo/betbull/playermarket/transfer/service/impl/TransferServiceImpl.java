package com.mihajlo.betbull.playermarket.transfer.service.impl;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.TransferStatus;
import com.mihajlo.betbull.playermarket.transfer.exception.error.EntityException;
import com.mihajlo.betbull.playermarket.transfer.exception.error.InputException;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.request.UpdateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerTeamResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TeamResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TransferResponse;
import com.mihajlo.betbull.playermarket.transfer.repository.TransferRepository;
import com.mihajlo.betbull.playermarket.transfer.service.TransferService;
import com.mihajlo.betbull.playermarket.transfer.service.feign.PlayerTeamFeignService;
import org.assertj.core.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransferServiceImpl implements TransferService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);

    private TransferRepository transferRepository;
    private PlayerTeamFeignService playerTeamFeignService;

    public TransferServiceImpl(TransferRepository transferRepository, PlayerTeamFeignService playerTeamFeignService) {
        this.transferRepository = transferRepository;
        this.playerTeamFeignService = playerTeamFeignService;
    }

    @Override
    public List<Transfer> getAll() {
        return transferRepository.findAllActive();
    }

    @Override
    public Transfer getById(Long id) {
        Optional<Transfer> transfer = transferRepository.findById(id);
        if (!transfer.isPresent()) {
            LOGGER.info("Transfer with id = {} was not found in the database.", id);
            throw new EntityException("Transfer with id " + id + " was not found");
        }
        return transfer.get();
    }

    @Override
    public TransferResponse getTransfer(Long transferId) {
        Transfer transfer = getById(transferId);
        return new TransferResponse(transfer);
    }

    @Override
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void delete(Transfer transfer) {
        transfer.setStatus(TransferStatus.DELETED);
        transferRepository.save(transfer);
    }

    @Override
    public void deleteById(Long id) {
        Preconditions.checkNotNull(id, "Transfer id is required");
        LOGGER.info("Deleting transfer with id = {}", id);
        Transfer transfer = getById(id);
        transfer.setStatus(TransferStatus.DELETED);
        transferRepository.save(transfer);
        LOGGER.info("Transfer with id = {} has been deleted (flagged)", id);
    }

    @Override
    @Transactional
    public TransferResponse createTransfer(Long playerId, CreateTransferRequest request) {
        PlayerResponse player = playerTeamFeignService.getPlayerById(playerId);
        TeamResponse newTeam = playerTeamFeignService.getTeamById(request.getTeamId());

        LocalDate transferDate = null;

        try {
            transferDate = LocalDate.parse(request.getTransferDate());
        }
        catch (DateTimeParseException ex) {
            LOGGER.info("Could not parse date = {}", request.getTransferDate());
            throw new InputException("You've entered an invalid date. Please enter date in format 'YYYY-MM-DD'");
        }

        checkIfPlayerCanTransfer(playerId, newTeam, transferDate, false);
        Transfer transfer = calculateFees(player, newTeam, transferDate, null);
        Transfer newTransfer = save(transfer);

        return new TransferResponse(newTransfer);
    }

    @Override
    @Transactional
    public TransferResponse updateTransfer(Long playerId, Long transferId, UpdateTransferRequest request) {
        PlayerResponse player = playerTeamFeignService.getPlayerById(playerId);
        Transfer playersTransfer = transferRepository.findPlayersTransfer(playerId, transferId);

        if (playersTransfer == null) {
            throw new EntityException("Transfer ("+transferId+") not found for player ("+playerId+")");
        }

        TeamResponse newTeam = playerTeamFeignService.getTeamById(request.getTeamId());

        LocalDate transferDate = null;

        try {
            transferDate = LocalDate.parse(request.getTransferDate());
        }
        catch (DateTimeParseException ex) {
            LOGGER.info("Could not parse date = {}", request.getTransferDate());
            throw new InputException("You've entered an invalid date. Please enter date in format 'YYYY-MM-DD'");
        }

        checkIfPlayerCanTransfer(playerId, newTeam, transferDate, true);
        Transfer updatedTransfer = calculateFees(player, newTeam, transferDate, playersTransfer);
        Transfer savedTransfer = save(updatedTransfer);

        return new TransferResponse(savedTransfer);
    }

    private void checkIfPlayerCanTransfer(Long playerId, TeamResponse newTeam, LocalDate transferDate, boolean update) {
        Transfer lastTransfer = transferRepository.findPlayersLastTransfer(playerId);

        if (lastTransfer != null) {
            TeamResponse currentTeam = playerTeamFeignService.getTeamById(lastTransfer.getTeamId());

            if (currentTeam == null) {
                throw new EntityException("New team not found");
            }

            if (currentTeam.getId().equals(newTeam.getId()) && !update) {
                throw new InputException("Cannot transfer player to the same team");
            }

            if (transferDate.isBefore(lastTransfer.getTransferDate().plusDays(1))) {
                throw new InputException("Cannot create a transfer before the current one ("+lastTransfer.getTransferDate()+")");
            }

            if (!update) {
                lastTransfer.setStatus(TransferStatus.INACTIVE);
                save(lastTransfer);
            }
        }
    }

    private Transfer calculateFees(PlayerResponse player, TeamResponse team, LocalDate transferDate, Transfer existingTransfer) {
        double feePerMonthOfExperience = 100000.00;

        Period periodCareerStart = Period.between(player.getCareerStartDate(), transferDate);
        int monthsOfExperience = periodCareerStart.getYears() * 12 + periodCareerStart.getMonths();

        Period periodAge = Period.between(player.getBirthDate(), transferDate);
        int playerAge = periodAge.getYears();

        double transferFee = monthsOfExperience * feePerMonthOfExperience / playerAge;
        double teamCommission = transferFee * team.getCommissionPercentage() / 100;
        double contractFee = transferFee + teamCommission;

        if (existingTransfer != null) {
            existingTransfer.update(team, transferDate, transferFee, teamCommission, contractFee);
            return existingTransfer;
        }

        Transfer transfer = Transfer.create(player, team, transferDate, transferFee, teamCommission, contractFee);
        return transfer;
    }

    @Override
    public List<PlayerTeamResponse> getPlayerTeams(Long playerId) {
        PlayerResponse player = playerTeamFeignService.getPlayerById(playerId);
        Set<Long> playerTeamIds = transferRepository.findPlayerTeams(playerId);

        if (playerTeamIds.isEmpty()) {
            throw new EntityException("Player " + player.getFirstName() + " " + player.getLastName() + " has not played for any team");
        }

        List<PlayerTeamResponse> teams = new ArrayList<>();
        playerTeamIds.forEach(id -> {
            TeamResponse team = playerTeamFeignService.getTeamById(id);
            PlayerTeamResponse playerTeamResponse = new PlayerTeamResponse(team);
            teams.add(playerTeamResponse);
        });

        return teams;
    }

    @Override
    public void deleteByPlayer(Long playerId) {
        transferRepository.deleteByPlayer(playerId);
    }

    @Override
    public void deleteByTeam(Long teamId) {
        transferRepository.deleteByTeam(teamId);
    }
}
