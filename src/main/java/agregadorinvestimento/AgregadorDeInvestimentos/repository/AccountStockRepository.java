package agregadorinvestimento.AgregadorDeInvestimentos.repository;

import agregadorinvestimento.AgregadorDeInvestimentos.entity.AccountStock;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.AccountStockId;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
