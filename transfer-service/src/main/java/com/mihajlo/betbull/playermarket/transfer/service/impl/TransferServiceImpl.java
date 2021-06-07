package com.mihajlo.betbull.playermarket.transfer.service.impl;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.TransferStatus;
import com.mihajlo.betbull.playermarket.transfer.exception.error.EntityException;
import com.mihajlo.betbull.playermarket.transfer.exception.error.InputException;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerResponse;
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
import java.util.List;
import java.util.Optional;

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
        Transfer lastTransfer = transferRepository.findPlayersLastTransfer(playerId);

        LocalDate transferDate = null;

        try {
            transferDate = LocalDate.parse(request.getTransferDate());
        }
        catch (DateTimeParseException ex) {
            LOGGER.info("Could not parse date = {}", request.getTransferDate());
            throw new InputException("You've entered an invalid date. Please enter date in format 'YYYY-MM-DD'");
        }

        if (lastTransfer != null) {
            TeamResponse currentTeam = playerTeamFeignService.getTeamById(lastTransfer.getTeamId());

            if (currentTeam == null) {
                throw new EntityException("New team not found");
            }

            if (currentTeam.getId().equals(newTeam.getId())) {
                throw new InputException("Cannot transfer player to the same team");
            }

            if (transferDate.isBefore(lastTransfer.getTransferDate())) {
                throw new InputException("Cannot create a transfer before the current one ("+lastTransfer.getTransferDate()+")");
            }

            lastTransfer.setStatus(TransferStatus.INACTIVE);
            save(lastTransfer);
        }

        double feePerMonthOfExperience = 100000.00;

        Period periodCareerStart = Period.between(player.getCareerStartDate(), transferDate);
        int monthsOfExperience = periodCareerStart.getYears() * 12 + periodCareerStart.getMonths();

        Period periodAge = Period.between(player.getBirthDate(), transferDate);
        int playerAge = periodAge.getYears();

        double transferFee = monthsOfExperience * feePerMonthOfExperience / playerAge;
        double teamCommission = transferFee * newTeam.getCommissionPercentage() / 100;
        double contractFee = transferFee + teamCommission;

        Transfer transfer = Transfer.create(player, newTeam, transferDate, transferFee, teamCommission, contractFee);
        Transfer newTransfer = save(transfer);

        return new TransferResponse(newTransfer);
    }

//    @Override
//    public List<PlayerTransferResponse> getPlayerTeams(Long playerId) {
//        Player player = playerService.getById(playerId);
//        List<Transfer> playerTransfers = transferRepository.findPlayerTrasfers(playerId);
//        if (playerTransfers.isEmpty()) {
//            throw new EntityException("Player " + player.getFirstName() + " " + player.getLastName() + " has not played for any team");
//        }
//        return playerTransfers.stream().map(PlayerTransferResponse::new).collect(Collectors.toList());
//    }
}
