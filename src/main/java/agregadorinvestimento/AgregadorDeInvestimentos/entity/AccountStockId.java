package agregadorinvestimento.AgregadorDeInvestimentos.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AccountStockId {


    @Column(name = "accountId")
    private UUID accountId;


    @Column(name = "stockId")
    private String stockId;

    public AccountStockId() {
    }

    public AccountStockId(UUID accountId, String stockId) {
        this.accountId = accountId;
        this.stockId = stockId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
