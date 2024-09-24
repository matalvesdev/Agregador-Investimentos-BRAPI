package agregadorinvestimento.AgregadorDeInvestimentos.repository;

import agregadorinvestimento.AgregadorDeInvestimentos.entity.Stock;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
