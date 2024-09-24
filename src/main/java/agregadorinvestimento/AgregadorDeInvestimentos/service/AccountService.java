package agregadorinvestimento.AgregadorDeInvestimentos.service;


import agregadorinvestimento.AgregadorDeInvestimentos.client.BrapiClient;

import agregadorinvestimento.AgregadorDeInvestimentos.controller.dto.AccountStockDto;
import agregadorinvestimento.AgregadorDeInvestimentos.controller.dto.AccountStockResponseDto;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.AccountStock;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.AccountStockId;
import agregadorinvestimento.AgregadorDeInvestimentos.repository.AccountRepository;
import agregadorinvestimento.AgregadorDeInvestimentos.repository.AccountStockRepository;
import agregadorinvestimento.AgregadorDeInvestimentos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("${TOKEN}")
    private String TOKEN;

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;


    public AccountService(AccountRepository accountRepository,
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository,
                          BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


    public void accountStockDto(String accountId, AccountStockDto accountStockDto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(accountStockDto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                accountStockDto.quantity()

        );

        accountStockRepository.save(entity);

    }

    public List<AccountStockResponseDto> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStockResponseDto(as.getStock().getStockId(), as.getQuantity(), getTotal(as.getStock().getStockId(), as.getQuantity())))
                .toList();


    }

    private double getTotal(String stockId, Integer quantity) {

        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }
}
