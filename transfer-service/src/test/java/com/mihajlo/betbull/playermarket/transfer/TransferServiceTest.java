package com.mihajlo.betbull.playermarket.transfer;

import com.mihajlo.betbull.playermarket.transfer.entity.Transfer;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.Currency;
import com.mihajlo.betbull.playermarket.transfer.entity.domain.TransferStatus;
import com.mihajlo.betbull.playermarket.transfer.model.request.CreateTransferRequest;
import com.mihajlo.betbull.playermarket.transfer.model.response.PlayerResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TeamResponse;
import com.mihajlo.betbull.playermarket.transfer.model.response.TransferResponse;
import com.mihajlo.betbull.playermarket.transfer.repository.TransferRepository;
import com.mihajlo.betbull.playermarket.transfer.service.TransferService;
import com.mihajlo.betbull.playermarket.transfer.service.feign.PlayerTeamFeignService;
import com.mihajlo.betbull.playermarket.transfer.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TransferServiceTest.class);

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private PlayerTeamFeignService playerTeamFeignService;

    @InjectMocks
    private TransferService transferService = new TransferServiceImpl(transferRepository, playerTeamFeignService);

    @Test
    public void createTransferSuccess() {
        Long playerId = 5L;
        LocalDate birthDate = LocalDate.of(1998, Month.SEPTEMBER, 21);
        LocalDate careerStartDate = LocalDate.of(2016, Month.DECEMBER, 4);
        PlayerResponse player = new PlayerResponse(playerId, "John", "Doe", birthDate, careerStartDate);

        Long teamId = 4L;
        TeamResponse team = new TeamResponse(teamId, "FC Best Team", 8.32, Currency.USD);

        when(playerTeamFeignService.getPlayerById(playerId)).thenReturn(player);
        when(playerTeamFeignService.getTeamById(teamId)).thenReturn(team);

        when(transferRepository.findPlayersLastTransfer(playerId)).thenReturn(null);
        when(transferRepository.save(any(Transfer.class))).thenAnswer(i -> {
            Transfer inserted = i.getArgument(0);
            inserted.setId(7L);
            return inserted;
        });

        CreateTransferRequest request = new CreateTransferRequest();
        request.setTeamId(teamId);
        request.setTransferDate("2020-06-11");

        TransferResponse response = transferService.createTransfer(playerId, request);

        assertNotNull(response);
        assertEquals(Currency.USD, response.getCurrency());
        assertEquals(8.32, response.getCommissionPercentage());
        assertTrue(response.getTransferFee() > 0);
        assertTrue(response.getTeamCommission() > 0);
        assertTrue(response.getContractFee() > 0);
        assertEquals(TransferStatus.ACTIVE, response.getStatus());

        LocalDate transferDate = LocalDate.of(2020, Month.JUNE, 11);
        assertEquals(transferDate, response.getTransferDate());
    }
}
